package com.amazon.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        indexes = {
                @Index(name = "idx_product_id", columnList = "productId")
        }
)
public class ProductDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productId;

    @Column(length = 2000)
    private String description;

}
