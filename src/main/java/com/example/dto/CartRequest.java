package com.example.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CartRequest {
    @NotEmpty
    private List<CartItem> items;

    @NotBlank
    private String customerSegment;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CartItem {
        @NotNull(message = "Product ID must be provided")
        private UUID productId;
        @Min(value = 1, message = "Quantity must be at least 1")
        private int qty;
    }
}
