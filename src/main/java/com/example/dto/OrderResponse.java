// OrderResponse.java
package com.example.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderResponse {
    private String orderId;
    private BigDecimal total;
    private String status;
}
