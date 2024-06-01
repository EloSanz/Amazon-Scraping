package com.amazon.repository;

import com.amazon.model.ProductDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Long> {

    ProductDescription findByProductId(String productId);
}
