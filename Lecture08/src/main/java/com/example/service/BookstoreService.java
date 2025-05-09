package com.example.service;

import com.example.entity.Book;
import com.example.entity.Bookstore;
import com.example.repository.BookRepository;
import com.example.repository.BookstoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookstoreService {
    @Autowired
    private BookstoreRepository bookstoreRepository;
    private BookRepository bookRepository;
    public List<Bookstore> getAllBookstores() {
        return bookstoreRepository.findAll();
    }

    public Bookstore getBookstoreById(Long id) {
        return bookstoreRepository.findById(id).orElseThrow(() -> new RuntimeException("Bookstore not found!"));
    }

    public Bookstore createBookstore(Bookstore bookstore) {
        return bookstoreRepository.save(bookstore);
    }

    public Bookstore updateBookstore(Long bookstoreId, Bookstore bookstore) {
        return bookstoreRepository.findById(bookstoreId).map(bookstore1 -> {
            bookstore1.setName(bookstore.getName());
            bookstore1.setAddress(bookstore.getAddress());
            return bookstoreRepository.save(bookstore1);
        }).orElseThrow(() -> new RuntimeException("Bookstore not found!"));
    }

    public void deleteBookstore(Long id) {
        bookstoreRepository.findById(id).ifPresent(bookstore -> {
            bookstoreRepository.deleteById(id);
        });
    }

    public Bookstore addBookToBookstore(Long bookstoreId, Book book) {
        return bookstoreRepository.findById(bookstoreId).map(bookstore -> {
            bookstore.getBooks().add(book);
            book.setBookstore(bookstore);
            bookRepository.save(book);
            return bookstoreRepository.save(bookstore);
        }).orElseThrow(() -> new RuntimeException("Bookstore not found!"));
    }

    public void removeBookFromBookstore(Long bookstoreId, Long bookId) {
        bookstoreRepository.findById(bookstoreId).ifPresent(bookstore -> {
            bookRepository.findById(bookId).ifPresent(book -> {
                bookstore.getBooks().remove(book);
                bookstoreRepository.save(bookstore);
                bookRepository.deleteById(bookId);
            });
        });
    }
}
