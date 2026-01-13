package com.unir.example.bibloteca.repository;

import com.unir.example.bibloteca.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
