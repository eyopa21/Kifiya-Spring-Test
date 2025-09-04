package com.example.rules;

import com.example.entity.Promotion;
import com.example.entity.PromotionType;

public class RuleFactory {
    public static PromotionRule fromPromotion(Promotion p) {
        if (!p.isActive()) return (prod, qty, ctx) -> java.math.BigDecimal.ZERO;
        return switch (p.getType()) {
            case PERCENT_OFF_CATEGORY -> new PercentOffCategoryRule(p.getCategory(), p.getPercentOff());
            case BUY_X_GET_Y -> new BuyXGetYRule(p.getProduct().getId(), p.getBuyQty(), p.getFreeQty());
        };
    }
}
