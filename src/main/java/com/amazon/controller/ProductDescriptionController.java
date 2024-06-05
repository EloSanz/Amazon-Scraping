package com.amazon.controller;
import com.amazon.exception.ProductDescriptionNotFoundException;

import com.amazon.exception.ProductDescriptionNotSavedException;
import com.amazon.repository.ProductDescriptionRepository;
import com.amazon.service.ProductDescriptionService;
import com.amazon.model.ProductDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductDescriptionController {

    @Autowired
    private ProductDescriptionService service;
    @Autowired
    private ProductDescriptionRepository repository;
    @PostMapping("/fetch")
    public ProductDescription fetchProductDescription(@RequestParam String url) {
        String productId = service.extractProductId(url);
        ProductDescription productDescription = repository.findByProductId(productId);

        if (productDescription != null) {
            return productDescription;
        } else {
            try {
                return service.fetchAndSaveDescription(url);
            } catch (ProductDescriptionNotSavedException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }
    @ControllerAdvice
    public static class GlobalExceptionHandler {
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
    @GetMapping("/test")
    public String testEndpoint() {
        return "testing";
    }

    @GetMapping("/{productId}/word-frequency")
    public Map<String, Integer> getWordFrequency(@PathVariable String productId) {
        try {
            ProductDescription productDescription = repository.findByProductId(productId);
            if (productDescription != null)
                return service.getWordFrequency(productId);
            else {
                ProductDescription fetchedDescription = fetchProductDescription("https://www.amazon.com/gp/product/" + productId);
                if (fetchedDescription == null)
                    throw new ProductDescriptionNotFoundException("Product description not found for ID: " + productId);
                else
                    return service.getWordFrequency(fetchedDescription.getProductId());
            }
        } catch (ProductDescriptionNotFoundException e) {
            e.printStackTrace();
            Map<String, Integer> errorMap = new HashMap<>();
            errorMap.put("error, product ID: " + productId + " without description", 0);
            return errorMap;
        }
    }

    @GetMapping
    public List<ProductDescription> getAllProductDescriptions() {
        return service.getAllProductDescriptions();
    }
}
