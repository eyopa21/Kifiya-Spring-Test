package com.example.mapper;

import com.example.dto.CartQuoteResponse;
import com.example.entity.Product;

import java.math.BigDecimal;

public class CartMapper {

    public static CartQuoteResponse.LineItem toLineItem(Product product, int qty, BigDecimal discount) {
        BigDecimal unitPrice = product.getPrice();
        BigDecimal totalBeforeDiscount = unitPrice.multiply(BigDecimal.valueOf(qty));
        BigDecimal finalPrice = totalBeforeDiscount.subtract(discount != null ? discount : BigDecimal.ZERO);

        return CartQuoteResponse.LineItem.builder()
                .productName(product.getName())
                .category(product.getCategory())
                .qty(qty)
                .unitPrice(unitPrice)
                .discount(discount != null ? discount : BigDecimal.ZERO)
                .finalPrice(finalPrice)
                .build();
    }


}
