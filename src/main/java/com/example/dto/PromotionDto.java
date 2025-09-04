// PromotionDto.java  (for creating a single promotion)
package com.example.dto;

import com.example.entity.PromotionType;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PromotionDto {
    @NotNull(message = "Promotion type must be specified")
    private PromotionType type;

    // for percent off
    private String category;

    // for buy x get y
    private UUID productId;


    @DecimalMin(value = "0.0", message = "Percent off cannot be less than 0")
    @DecimalMax(value = "100.0", message = "Percent off cannot exceed 100")
    private BigDecimal percentOff;

    @Min(value = 1, message = "Buy quantity must be at least 1")
    private Integer buyQty;

    @Min(value = 0, message = "Free quantity cannot be negative")
    private Integer freeQty;

    private Integer priority;
}
