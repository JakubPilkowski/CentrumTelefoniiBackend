package com.example.centrumtelefonii.dao;

import com.example.centrumtelefonii.models.Click;
import com.example.centrumtelefonii.models.Product;
import com.example.centrumtelefonii.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface PromotionRepo extends JpaRepository<Promotion, Integer> {


    @Query("SELECT p FROM Promotion p ORDER BY p.date ASC")
    List<Promotion> findAllSorted();

    Optional<Promotion> findByDate(LocalDate date);
}
