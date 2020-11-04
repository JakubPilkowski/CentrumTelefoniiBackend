package com.example.centrumtelefonii.dao;

import com.example.centrumtelefonii.models.JwtBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtBlackListRepo extends JpaRepository<JwtBlacklist, Long> {

    Optional<JwtBlacklist> findByTokenEquals(String token);
}
