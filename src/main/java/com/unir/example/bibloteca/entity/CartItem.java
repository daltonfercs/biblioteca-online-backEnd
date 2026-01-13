package com.unir.example.bibloteca.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private Integer quantity = 1;

    @Column(name = "rental_days")
    private Integer rentalDays = 7;

    @Column(name = "added_at")
    private LocalDateTime addedAt = LocalDateTime.now();
}