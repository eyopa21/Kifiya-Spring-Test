// PromotionService.java   (accept exactly ONE promotion, validate)
package com.example.service;

import com.example.dto.PromotionDto;
import com.example.entity.Product;
import com.example.entity.Promotion;
import com.example.entity.PromotionType;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.ProductRepository;
import com.example.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service @RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;

    public Promotion createPromotion(PromotionDto dto) {
        // validation based on type
        if (dto.getType() == PromotionType.PERCENT_OFF_CATEGORY) {
            if (dto.getCategory() == null || dto.getPercentOff() == null)
                throw new IllegalArgumentException("category and percentOff required");
            if (dto.getPercentOff().compareTo(BigDecimal.ZERO) < 0 ||
                    dto.getPercentOff().compareTo(BigDecimal.valueOf(100)) > 0)
                throw new IllegalArgumentException("percentOff must be 0..100");
        } else if (dto.getType() == PromotionType.BUY_X_GET_Y) {
            if (dto.getProductId() == null || dto.getBuyQty() == null || dto.getFreeQty() == null)
                throw new IllegalArgumentException("productId, buyQty, freeQty required");
        }

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Promotion promotion = Promotion.builder()
                .type(dto.getType())
                .category(dto.getCategory())
                .percentOff(dto.getPercentOff())
                .product(product)
                .buyQty(dto.getBuyQty())
                .freeQty(dto.getFreeQty())
                .priority(dto.getPriority())
                .build();

        return promotionRepository.save(promotion);
    }

    public List<Promotion> activePromotionsOrdered() {
        return promotionRepository.findByActiveTrueOrderByPriorityAsc();
    }

    private int defaultPriority(PromotionDto dto) {
        return switch (dto.getType()) {
            case PERCENT_OFF_CATEGORY -> 10; // run first
            case BUY_X_GET_Y -> 20;          // run after
        };
    }
}
