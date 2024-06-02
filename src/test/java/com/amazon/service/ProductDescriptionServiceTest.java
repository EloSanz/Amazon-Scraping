package com.amazon.service;

import com.amazon.model.ProductDescription;
import com.amazon.repository.ProductDescriptionRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductDescriptionServiceTest {

    @InjectMocks
    private ProductDescriptionService service;
    @Mock
    private ProductDescriptionRepository repository;

    @Test
    void testRemoveProductDescriptionHeader() {
        String description = "Product Description Sleek oCOSMO CE4031 brush pattern black 40\" 1080P LED MHL & Roku Ready HDTV features...";
        String expected = "Sleek oCOSMO CE4031 brush pattern black 40\" 1080P LED MHL & Roku Ready HDTV features...";

        String result = service.removeProductDescriptionHeader(description);
        Assertions.assertEquals(expected, result, "The header should be removed from the product description");
    }

    @Test
    void testFetchAndSaveDescription_WithEmptyDescription() throws IOException {

        String url = "https://www.amazon.com/gp/product/B00TSUGXKE";
        ProductDescription result = service.fetchAndSaveDescription(url);//
        assertNull(result);
    }




}