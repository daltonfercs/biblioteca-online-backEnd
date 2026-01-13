package com.unir.example.bibloteca.repository;

import com.unir.example.bibloteca.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findByUserId(Integer userId);
    List<Rental> findByUserIdAndStatus(Integer userId, String status);
}