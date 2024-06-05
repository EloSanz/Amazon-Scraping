package com.amazon.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
@Table(
        indexes = {
                @Index(name = "idx_url", columnList = "url")
        }
)
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)

    private String url;
    @Column(length = 200)
    private String errorMessage;

    public ErrorLog(String productUrl, String errorMessage) {
        this.url = productUrl;
        this.errorMessage = errorMessage;
    }

    public ErrorLog() {

    }
}
