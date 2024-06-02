package com.amazon.service;

import com.amazon.model.ProductDescription;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazon.repository.ProductDescriptionRepository;

import java.io.IOException;
import java.util.*;

@Service
public class ProductDescriptionService {

    @Autowired
    private ProductDescriptionRepository repository;

    public ProductDescription fetchAndSaveDescription(String productUrl) throws IOException {
        // Simulate a browser to fix the null div problem
        Connection connection = Jsoup.connect(productUrl).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");

        Document doc = connection.get();
        String productId = extractProductId(productUrl);
        String description = doc.select("#productDescription").text();

        // Eliminar el encabezado "Product Description"
        description = removeProductDescriptionHeader(description);

        ProductDescription productDescription = new ProductDescription();
        productDescription.setProductId(productId);
        productDescription.setDescription(description);

        if(description.isEmpty())
            return  null;//I'll not save empty descriptions

        return repository.save(productDescription);
    }
    public String removeProductDescriptionHeader(String description) {
        int startIndex = description.indexOf("Product Description");
        if (startIndex != -1) {
            // Si se encuentro el encabezado lo saco, junto a los espacios en blanco
            return description.substring(startIndex + "Product Description".length()).trim();
        } else {
            // Si no encuentro encabezado va directo
            return description;
        }
    }
    public String extractProductId(String productUrl) {
        // Example: https://www.amazon.com/gp/product/B00SMBFZNG -> B00SMBFZNG
        return productUrl.substring(productUrl.lastIndexOf('/') + 1);
    }
    public Map<String, Integer> getWordFrequency(String productId) {
        // This productId is Unique, so I'll use findByProductId from my repository.
        ProductDescription productDescription = repository.findByProductId(productId);
        if (productDescription == null) {
            return new HashMap<>();
        }
        // Set of words to filter out
        Set<String> filteredWords = new HashSet<>(Arrays.asList("the", "to", "you", "yours", "a", "an","and", "as",
                "it", "in", "with", "that", "its", "of", "are", "all", "is", "for", "where", "more"));

        String[] words = productDescription.getDescription().toLowerCase().split("\\s+|\\p{Punct}+");
        Map<String, Integer> wordCount = new HashMap<>();

        // Count word occurrences, filtering out empty strings, filtered words, and words containing numbers
        for (String word : words) {
            if (!word.isEmpty() && !filteredWords.contains(word) && !containsDigit(word)) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }
        // Convert map to list of entries
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(wordCount.entrySet());

        // Sort the list by value in descending order
        entryList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        // Create a new LinkedHashMap to maintain the order of entries
        Map<String, Integer> sortedWordCount = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedWordCount.put(entry.getKey(), entry.getValue());
        }

        return sortedWordCount;
    }

    // Method to check if a word contains a digit
    private boolean containsDigit(String word) {
        for (char c : word.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }


    public List<ProductDescription> getAllProductDescriptions() {
        return repository.findAll();
    }
}
