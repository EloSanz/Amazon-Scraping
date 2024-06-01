package com.amazon.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class ProductDescriptionServiceTest {

    @InjectMocks
    private ProductDescriptionService service;

    @Test
    void removeProductDescriptionHeader() {
        String description = "Product Description Sleek oCOSMO CE4031 brush pattern black 40\" 1080P LED MHL & Roku Ready HDTV features...";
        String expected = "Sleek oCOSMO CE4031 brush pattern black 40\" 1080P LED MHL & Roku Ready HDTV features...";

        String result = service.removeProductDescriptionHeader(description);

        Assertions.assertEquals(expected, result, "The header should be removed from the product description");
    }

}
