package com.amazon.exception;

public class ProductDescriptionNotSavedException extends RuntimeException{
    public ProductDescriptionNotSavedException(String message) {
        super(message);
    }

}
