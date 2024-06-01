package com.amazon.service;

import org.springframework.stereotype.Service;

@Service
public class ProductDescriptionService {
    public String removeProductDescriptionHeader(String description) {
        if (description.startsWith("Product Description")) {
            return description.substring("Product Description".length()).trim();
        }
        return description;
    }
}
