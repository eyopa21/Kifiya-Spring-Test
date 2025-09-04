package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "idempotency_keys", uniqueConstraints = @UniqueConstraint(columnNames = "keyValue"))
public class IdempotencyKey {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    @Column(nullable = false, length = 128)
    private String keyValue;


    @Column(nullable = false, length = 64)
    private String orderId;

    @Column(nullable = false, precision = 18, scale = 2)
    private java.math.BigDecimal total;
}
