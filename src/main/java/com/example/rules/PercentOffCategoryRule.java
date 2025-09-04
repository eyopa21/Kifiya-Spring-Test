package com.example.rules;

import com.example.entity.Product;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@RequiredArgsConstructor
public class PercentOffCategoryRule implements PromotionRule {
    private final String category;
    private final BigDecimal percent;

    @Override
    public BigDecimal apply(Product product, int qty, RuleContext ctx) {
        if (!product.getCategory().equalsIgnoreCase(category)) return BigDecimal.ZERO;
        BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(qty));
        BigDecimal discount = subtotal.multiply(percent).divide(BigDecimal.valueOf(100));
        discount = RuleContext.toMoney(discount);
        if (discount.signum() > 0) {
            ctx.addAudit(percent.stripTrailingZeros().toPlainString() + "% off " + category);
        }
        return discount.max(BigDecimal.ZERO);
    }
}
