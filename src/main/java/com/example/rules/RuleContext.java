package com.example.rules;

import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class RuleContext {
    private final List<String> audit = new ArrayList<>();

    public void addAudit(String msg) { audit.add(msg); }

    public static BigDecimal toMoney(BigDecimal v) {
        return v.setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
