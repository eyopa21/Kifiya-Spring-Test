package com.example.rules;

import com.example.entity.Product;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
public class BuyXGetYRule implements PromotionRule {
    private final UUID targetProductId;
    private final int buyX;
    private final int getY;

    @Override
    public BigDecimal apply(Product product, int qty, RuleContext ctx) {
        if (!product.getId().equals(targetProductId)) return BigDecimal.ZERO;
        int groups = qty / buyX;
        int freeUnits = groups * getY;
        BigDecimal discount = product.getPrice().multiply(BigDecimal.valueOf(freeUnits));
        discount = RuleContext.toMoney(discount);
        if (freeUnits > 0) {
            ctx.addAudit("Buy " + buyX + " Get " + getY + " free on " + product.getName());
        }
        return discount.max(BigDecimal.ZERO);
    }
}
