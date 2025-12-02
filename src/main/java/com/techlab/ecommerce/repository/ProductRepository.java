package com.techlab.ecommerce.repository;

import java.util.List;

import com.techlab.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);
}
