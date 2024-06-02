package com.amazon.controller;

import com.amazon.repository.ProductDescriptionRepository;
import com.amazon.service.ProductDescriptionService;
import com.amazon.model.ProductDescription;
import com.sun.jdi.PrimitiveValue;
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
    @Autowired
    private ProductDescriptionRepository repository;
    @PostMapping("/fetch")
    public ProductDescription fetchProductDescription(@RequestParam String url) throws IOException {
        System.out.println(url);
        String productId = service.extractProductId(url);
        ProductDescription productDescription = repository.findByProductId(productId);;
        if( productDescription != null) {
            return productDescription;
        }else {
            return service.fetchAndSaveDescription(url);
        }
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "testing";
    }

    @GetMapping("/{productId}/word-frequency")
    public Map<String, Integer> getWordFrequency(@PathVariable String productId) throws IOException {
        ProductDescription productDescription = repository.findByProductId(productId);

        if (productDescription != null) {
            return service.getWordFrequency(productId);
        } else { // not already in database, so fetch and save
            //System.out.println("fetching and saving " + productId);
            productDescription = fetchProductDescription(
                    "https://www.amazon.com/gp/product/" + productId);
            return service.getWordFrequency(productDescription.getProductId());
        }
    }

    @GetMapping
    public List<ProductDescription> getAllProductDescriptions() {
        return service.getAllProductDescriptions();
    }
}
