package com.example.centrumtelefonii.controllers;


import com.example.centrumtelefonii.dao.ProductRepo;
import com.example.centrumtelefonii.models.Click;
import com.example.centrumtelefonii.models.Product;
import com.example.centrumtelefonii.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    ProductRepo productRepo;

    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/produkty")
    public List<Product> getProducts(){
        return productRepo.findAll();
    }

    @GetMapping("/produkty/{id}")
    public Optional<Product> getProduct(@PathVariable("id") int id){
        return productRepo.findById(id);
    }


    @PutMapping("/produkt")
    public Product updateProduct(@RequestBody Product product) {
        productRepo.save(product);
        return product;
    }

    @DeleteMapping("/produkty/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("id") int id) {
        Product a = productRepo.getOne(id);
        productRepo.delete(a);
        return ResponseEntity.ok(new ApiResponse(true, "Poprawnie usunięto produkt"));
    }

    @DeleteMapping("/produkty")
    public ResponseEntity<ApiResponse> deleteProducts() {
        productRepo.deleteAll();
        return ResponseEntity.ok(new ApiResponse(true, "Poprawnie usunięto produkty"));
    }

    @PostMapping(path="/produkt", consumes="application/json")
    public Product addProduct(@RequestBody Product product) {
        productRepo.save(product);
        return product;
    }
}
