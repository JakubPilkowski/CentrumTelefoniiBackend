package com.example.centrumtelefonii.dao;


import com.example.centrumtelefonii.models.Image;
import com.example.centrumtelefonii.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Integer> {


//    @Query("SELECT p FROM Promotion p ORDER BY p.date ASC")
//    List<Promotion> findAllSorted();

}
