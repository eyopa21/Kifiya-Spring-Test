package com.example.rules;

import com.example.entity.Product;
import java.math.BigDecimal;

public interface PromotionRule {
    /**
     * @return discount amount to subtract from line subtotal (unitPrice * qty). Must be >= 0.
     */
    BigDecimal apply(Product product, int qty, RuleContext ctx);
}
