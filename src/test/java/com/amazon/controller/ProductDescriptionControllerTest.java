package com.amazon.controller;

import com.amazon.model.ProductDescription;
import com.amazon.repository.ProductDescriptionRepository;
import com.amazon.service.ProductDescriptionService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductDescriptionControllerTest {

    @InjectMocks
    private ProductDescriptionController controller;

    @Mock
    private ProductDescriptionService service;

    @Mock
    private ProductDescriptionRepository repository;

    @Test
    void testEndpoint() {
        String expected = "testing";
        String result = controller.testEndpoint();
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testFetchAndSaveDescription_without_Description() {
        // Arrange
        String url = "https://www.example.com/product/12345";

        String productId = service.fetchAndSaveDescription(url);
        Assertions.assertEquals(null, productId);
    }

    @Test
    void testGetWordFrequency_ProductExists() throws Exception {
        String productId = "B00SMBFZNG";
        ProductDescription productDescription = new ProductDescription();
        productDescription.setProductId(productId);
        productDescription.setDescription("Sample description");

        when(repository.findByProductId(productId)).thenReturn(productDescription);
        when(service.getWordFrequency(productId)).thenReturn(Map.of("sample", 1, "description", 1));

        Map<String, Integer> wordFrequency = controller.getWordFrequency(productId);

        Assertions.assertEquals(1, wordFrequency.get("sample"));
        Assertions.assertEquals(1, wordFrequency.get("description"));
    }
    @Test
    void testGetWordFrequency_ProductDoesNotExist(){
        String productId = "B00TSUGXKE";

        Map<String, Integer> errorMap = new HashMap<>();
        errorMap.put("error, product ID: " + productId + " without description", 0);

        Map<String, Integer> wordFrequency = controller.getWordFrequency(productId);

        Assertions.assertEquals(errorMap, wordFrequency);
    }

}
