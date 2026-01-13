package com.unir.example.bibloteca.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
@Data
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "rental_date")
    private LocalDateTime rentalDate = LocalDateTime.now();

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;

    // Asegúrate de usar el String exacto de tu ENUM en base de datos o mapearlo con @Enumerated
    private String status;

    @Column(name = "total_price")
    private BigDecimal totalPrice;
}