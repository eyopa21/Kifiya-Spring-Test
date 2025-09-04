// CartService.java  (pipeline + rounding + optimistic locking + idempotency)
package com.example.service;

import com.example.dto.*;
import com.example.entity.*;
import com.example.exception.StockConflictException;
import com.example.repository.IdempotencyKeyRepository;
import com.example.rules.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service @RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private final PromotionService promotionService;
    private final IdempotencyKeyRepository idempotencyRepo;

    public CartQuoteResponse calculateQuote(CartRequest request) {
        List<Promotion> defs = promotionService.activePromotionsOrdered();
        List<PromotionRule> rules = defs.stream().map(RuleFactory::fromPromotion).toList();

        List<CartQuoteResponse.LineItem> lineItems = new ArrayList<>();
        RuleContext ctx = new RuleContext();
        BigDecimal total = BigDecimal.ZERO;

        for (CartRequest.CartItem item : request.getItems()) {
            Product product = productService.getById(item.getProductId());
            BigDecimal unit = product.getPrice();
            BigDecimal lineSubtotal = unit.multiply(BigDecimal.valueOf(item.getQty()));
            lineSubtotal = RuleContext.toMoney(lineSubtotal);

            BigDecimal discount = BigDecimal.ZERO;
            for (PromotionRule rule : rules) {
                discount = discount.add(rule.apply(product, item.getQty(), ctx));
            }

            discount = discount.min(lineSubtotal).max(BigDecimal.ZERO);
            discount = RuleContext.toMoney(discount);

            BigDecimal finalPrice = lineSubtotal.subtract(discount);
            finalPrice = RuleContext.toMoney(finalPrice);

            lineItems.add(CartQuoteResponse.LineItem.builder()
                    .productName(product.getName())
                    .category(product.getCategory())
                    .qty(item.getQty())
                    .unitPrice(unit)
                    .discount(discount)
                    .finalPrice(finalPrice)
                    .build());

            total = total.add(finalPrice);
        }

        total = RuleContext.toMoney(total);
        return CartQuoteResponse.builder()
                .items(lineItems)
                .appliedPromotions(ctx.getAudit())
                .total(total)
                .build();
    }

    @Transactional
    public OrderResponse confirmOrder(CartRequest request, String idempotencyKey) {
        // Idempotency
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            var existing = idempotencyRepo.findByKeyValue(idempotencyKey);
            if (existing.isPresent()) {
                return OrderResponse.builder()
                        .orderId(existing.get().getOrderId())
                        .total(existing.get().getTotal())
                        .status("CONFIRMED")
                        .build();
            }
        }


        CartQuoteResponse quote = calculateQuote(request);

        // Decrement stock with optimistic locking
        for (CartRequest.CartItem item : request.getItems()) {
            Product p = productService.getById(item.getProductId());
            if (p.getStock() < item.getQty()) {
                throw new StockConflictException("Insufficient stock for product " + p.getName());
            }
            p.setStock(p.getStock() - item.getQty());
            productService.save(p);
        }

        String orderId = "ORD-" + UUID.randomUUID();
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            idempotencyRepo.save(IdempotencyKey.builder()
                    .keyValue(idempotencyKey)
                    .orderId(orderId)
                    .total(quote.getTotal())
                    .build());
        }

        return OrderResponse.builder()
                .orderId(orderId)
                .total(quote.getTotal())
                .status("CONFIRMED")
                .build();
    }


}
