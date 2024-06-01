package com.amazon.controller;

import com.amazon.AmazonScrappingApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = AmazonScrappingApplication.class)
@AutoConfigureMockMvc
class ProductDescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFetchProductDescription() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/products/fetch")
                            .param("url", "https://www.amazon.com/gp/product/B00SMBFZNG"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.productId").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.description").exists());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test failed due to exception: " + e.getMessage(), e);
        }
    }
}
