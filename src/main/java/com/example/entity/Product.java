// Product.java
package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "products")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank @Column(nullable = false, unique = true)
    private String name;

    @NotBlank @Column(nullable = false)
    private String category;

    @NotNull @DecimalMin(value = "0.00")
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @Min(0) @Column(nullable = false)
    private int stock;


}
