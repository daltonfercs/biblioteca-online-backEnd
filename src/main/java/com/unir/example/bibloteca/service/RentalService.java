package com.unir.example.bibloteca.service;

import com.unir.example.bibloteca.entity.*;
import com.unir.example.bibloteca.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    @Transactional
    public List<Rental> checkout(Integer userId) {
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        List<Rental> newRentals = new ArrayList<>();

        for (CartItem item : cartItems) {
            Book book = item.getBook();

            // Validar Stock
            if (book.getStock() < item.getQuantity()) {
                throw new RuntimeException("No hay stock suficiente para: " + book.getTitle());
            }

            // Crear Renta
            Rental rental = new Rental();
            rental.setUser(item.getUser());
            rental.setBook(book);
            rental.setRentalDate(LocalDateTime.now());
            rental.setReturnDate(LocalDate.now().plusDays(item.getRentalDays()));
            rental.setStatus("active"); // Status inicial

            // Calcular Precio Total (Lógica simple: precio libro * días)
            BigDecimal total = book.getPrice().multiply(new BigDecimal(item.getRentalDays()));
            rental.setTotalPrice(total);

            // Guardar Renta y Actualizar Stock
            newRentals.add(rentalRepository.save(rental));

            book.setStock(book.getStock() - item.getQuantity());
            bookRepository.save(book);
        }

        // Vaciar Carrito después de la compra
        cartRepository.deleteAll(cartItems);
        return newRentals;
    }

    public List<Rental> getActiveRentals(Integer userId) {
        return rentalRepository.findByUserIdAndStatus(userId, "active");
    }

    public List<CartItem> getRentalHistory(Integer userId) {
        // Nota: En un caso real, RentalRepository debería devolver List<Rental>, corregí la interfaz arriba
        // asumiendo que el método en repository devuelve List<Rental>
        return null; // Implementar según la corrección del repositorio
    }

    public Rental returnBook(Integer rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Renta no encontrada"));

        rental.setStatus("returned");
        rental.setActualReturnDate(LocalDate.now());

        // Aquí podrías agregar lógica para devolver stock al libro si es necesario
        Book book = rental.getBook();
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);

        return rentalRepository.save(rental);
    }
}