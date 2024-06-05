package com.amazon.exception;

public class ProductDescriptionNotFoundException extends RuntimeException {
    public ProductDescriptionNotFoundException(String message) {
        super(message);
    }
}
