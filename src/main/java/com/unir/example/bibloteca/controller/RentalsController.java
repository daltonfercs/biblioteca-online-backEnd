package com.unir.example.bibloteca.controller;

import com.unir.example.bibloteca.entity.Rental;
import com.unir.example.bibloteca.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalsController {

    private final RentalService rentalService;

    @PostMapping("/checkout")
    public ResponseEntity<List<Rental>> checkout(@RequestParam Integer userId) {
        return ResponseEntity.ok(rentalService.checkout(userId));
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<Rental>> getActiveRentals(@PathVariable Integer userId) {
        return ResponseEntity.ok(rentalService.getActiveRentals(userId));
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Rental> returnBook(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.returnBook(id));
    }
}