package com.example.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CartQuoteResponse {
    private List<LineItem> items;
    private List<String> appliedPromotions;
    private BigDecimal total;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class LineItem {
        private String productName;
        private String category;
        private int qty;
        private BigDecimal unitPrice;
        private BigDecimal discount;
        private BigDecimal finalPrice;
    }
}
