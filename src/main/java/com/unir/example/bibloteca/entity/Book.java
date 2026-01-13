package com.unir.example.bibloteca.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String author;
    private String isbn;
    private String category;
    private boolean available;
    private BigDecimal price;
    private Integer stock;
}