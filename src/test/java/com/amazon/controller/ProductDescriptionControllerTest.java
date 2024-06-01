package com.amazon.controller;

import com.amazon.model.ProductDescription;
import com.amazon.repository.ProductDescriptionRepository;
import com.amazon.service.ProductDescriptionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductDescriptionControllerTest {
    @Mock
    private ProductDescriptionController controller;

    @InjectMocks
    private ProductDescriptionService service;
    @Mock
    private ProductDescriptionRepository repository;

    @Test
    void testEndpoint() {
        String expected = "testing";
        when(controller.testEndpoint()).thenReturn(expected);

        String result = controller.testEndpoint();

        Assertions.assertEquals(expected, result);
    }
    @Test
    void testFetchProductDescription() throws Exception {

        ProductDescription mockedDescription = new ProductDescription();
        mockedDescription.setProductId("B00SMBFZNG");
        when(repository.save(any(ProductDescription.class))).thenReturn(mockedDescription);

        ProductDescription result = service.fetchAndSaveDescription("https://www.amazon.com/gp/product/B00SMBFZNG");
        Assertions.assertEquals("B00SMBFZNG", result.getProductId());
    }

}
