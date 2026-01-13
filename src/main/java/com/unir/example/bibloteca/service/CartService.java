package com.unir.example.bibloteca.service;

import com.unir.example.bibloteca.dto.CartRequestDto;
import com.unir.example.bibloteca.entity.*;
import com.unir.example.bibloteca.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<CartItem> getUserCart(Integer userId) {
        return cartRepository.findByUserId(userId);
    }

    public CartItem addToCart(Integer userId, CartRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Verificar si ya existe en el carrito para actualizarlo o crear uno nuevo
        CartItem item = cartRepository.findByUserIdAndBookId(userId, request.getBookId())
                .orElse(new CartItem());

        item.setUser(user);
        item.setBook(book);
        item.setQuantity(request.getQuantity());
        item.setRentalDays(request.getRentalDays());

        return cartRepository.save(item);
    }

    @Transactional
    public void removeFromCart(Integer userId, Integer bookId) {
        CartItem item = cartRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado en carrito"));
        cartRepository.delete(item);
    }

    @Transactional
    public void clearCart(Integer userId) {
        cartRepository.deleteByUserId(userId);
    }

    public BigDecimal calculateTotal(Integer userId) {
        List<CartItem> items = cartRepository.findByUserId(userId);
        return items.stream()
                .map(item -> item.getBook().getPrice()
                        .multiply(new BigDecimal(item.getRentalDays()))) // Ejemplo: Precio x Dias
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}