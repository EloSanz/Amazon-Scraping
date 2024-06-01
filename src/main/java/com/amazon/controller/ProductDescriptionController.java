package com.amazon.controller;

import com.amazon.service.ProductDescriptionService;
import com.amazon.model.ProductDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductDescriptionController {
    @Autowired
    private ProductDescriptionService service;

    @PostMapping("/fetch")
    public ProductDescription fetchProductDescription(@RequestParam String url) throws IOException {
        System.out.println(url);
        return service.fetchAndSaveDescription(url);
    }

    /*
    @GetMapping("/test")
    public String testEndpoint() {
        return "testing ! ! ";
    }
    */
    @GetMapping("/{productId}/word-frequency")
    public Map<String, Integer> getWordFrequency(@PathVariable String productId) {
        return service.getWordFrequency(productId);
    }

    @GetMapping
    public List<ProductDescription> getAllProductDescriptions() {
        return service.getAllProductDescriptions();
    }
}
