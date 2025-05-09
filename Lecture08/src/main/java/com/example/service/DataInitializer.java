package com.example.service;

import com.example.entity.Book;
import com.example.entity.Bookstore;
import com.example.repository.BookRepository;
import com.example.repository.BookstoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository, BookstoreRepository bookstoreRepository) {
        return args -> {
            List<Bookstore> bookstores = bookstoreRepository.saveAll(List.of(
                    new Bookstore("First Bookstore", "Address 1"),
                    new Bookstore("Second Bookstore", "Address 2")
            ));

            bookRepository.saveAll(List.of(
                    new Book("Book 1", "Author 1", bookstores.get(0)),
                    new Book("Book 2", "Author 2", bookstores.get(1)),
                    new Book("Book 3", "Author 3", bookstores.get(0))
            ));
        };
    }
}
