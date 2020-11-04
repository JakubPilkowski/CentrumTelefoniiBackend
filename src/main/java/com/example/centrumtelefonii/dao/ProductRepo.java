package com.example.centrumtelefonii.dao;

import com.example.centrumtelefonii.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
