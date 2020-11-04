package com.example.centrumtelefonii.dao;

import com.example.centrumtelefonii.models.Click;
import com.example.centrumtelefonii.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ClickRepo extends JpaRepository<Click, Integer> {

    @Query("SELECT c FROM Click c ORDER BY c.date ASC")
    List<Click> findAllSorted();

    Optional<Click> findByDate(LocalDate date);
}
