package com.unir.example.bibloteca.repository;

import com.unir.example.bibloteca.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUserId(Integer userId);
    Optional<CartItem> findByUserIdAndBookId(Integer userId, Integer bookId);
    void deleteByUserId(Integer userId);
}