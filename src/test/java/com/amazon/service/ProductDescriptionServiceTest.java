package com.amazon.service;

import com.amazon.exception.ProductDescriptionNotFoundException;
import com.amazon.repository.ProductDescriptionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductDescriptionServiceTest {

    @InjectMocks
    private ProductDescriptionService service;

    @Test
    void testRemoveProductDescriptionHeader() {
        String description = "Product Description Sleek oCOSMO CE4031 brush pattern black 40\" 1080P LED MHL & Roku Ready HDTV features...";
        String expected = "Sleek oCOSMO CE4031 brush pattern black 40\" 1080P LED MHL & Roku Ready HDTV features...";

        String result = service.removeProductDescriptionHeader(description);
        Assertions.assertEquals(expected, result, "The header should be removed from the product description");
    }

    @Test
    void testFetchAndSaveDescription_WithoutDescription() {
        // Product without description
        String url = "https://www.amazon.com/gp/product/B00TSUGXKE";

        ProductDescriptionService service = new ProductDescriptionService();
        assertThrows(NullPointerException.class, () -> {
            service.fetchAndSaveDescription(url);
        });
    }


}