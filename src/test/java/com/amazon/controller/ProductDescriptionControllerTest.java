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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

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
    void testFetchProductDescription() throws Exception {
        String url = "https://www.amazon.com/gp/product/B00SMBFZNG";
        String expectedProductId = "B00SMBFZNG";

        ProductDescription mockedDescription = new ProductDescription();
        mockedDescription.setProductId(expectedProductId);

        when(service.fetchAndSaveDescription(any(String.class))).thenReturn(mockedDescription);

        ProductDescription result = (ProductDescription) controller.fetchProductDescription(url);

        Assertions.assertEquals(expectedProductId, result.getProductId());
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
    void testGetWordFrequency_ProductDoesNotExist() throws Exception {
        String productId = "B00SMBFZNG";
        ProductDescription productDescription = new ProductDescription();
        productDescription.setProductId(productId);
        productDescription.setDescription("Sample description");

        when(repository.findByProductId(productId)).thenReturn(null);
        when(service.fetchAndSaveDescription(any(String.class))).thenReturn(productDescription);
        when(service.getWordFrequency(productId)).thenReturn(Map.of("sample", 1, "description", 1));

        Map<String, Integer> wordFrequency = controller.getWordFrequency(productId);

        Assertions.assertEquals(1, wordFrequency.get("sample"));
        Assertions.assertEquals(1, wordFrequency.get("description"));
    }
}
