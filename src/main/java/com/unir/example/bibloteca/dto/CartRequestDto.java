package com.unir.example.bibloteca.dto;

import lombok.Data;

@Data
public class CartRequestDto {
    private Integer bookId;
    private Integer quantity;
    private Integer rentalDays;
}