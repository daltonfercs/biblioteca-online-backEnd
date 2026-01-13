package com.unir.example.bibloteca.controller;

import com.unir.example.bibloteca.dto.CartRequestDto;
import com.unir.example.bibloteca.entity.CartItem;
import com.unir.example.bibloteca.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCart(@PathVariable Integer userId) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<CartItem> addToCart(@PathVariable Integer userId, @RequestBody CartRequestDto request) {
        return ResponseEntity.ok(cartService.addToCart(userId, request));
    }

    @DeleteMapping("/{userId}/items/{bookId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Integer userId, @PathVariable Integer bookId) {
        cartService.removeFromCart(userId, bookId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Integer userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/total")
    public ResponseEntity<BigDecimal> getCartTotal(@PathVariable Integer userId) {
        return ResponseEntity.ok(cartService.calculateTotal(userId));
    }
}