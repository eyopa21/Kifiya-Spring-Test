package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "promotions")
public class Promotion {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PromotionType type;

    // For PERCENT_OFF_CATEGORY
    private String category; // required when type=PERCENT_OFF_CATEGORY

    @DecimalMin("0.0") @DecimalMax("100.0")
    private BigDecimal percentOff;

    // For BUY_X_GET_Y
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Min(1) private Integer buyQty;
    @Min(1) private Integer freeQty;

    // Execution order (lower runs first). e.g., category % before BxGy
    @Builder.Default
    @Column(nullable = false)
    private Integer priority = 100;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;
}
