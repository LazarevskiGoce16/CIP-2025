package com.example.controller;

import com.example.entity.Bookstore;
import com.example.service.BookService;
import com.example.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookstores")
public class BookstoreController {
    @Autowired
    private BookstoreService bookstoreService;
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Bookstore> getAllBookstores() {
        return bookstoreService.getAllBookstores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bookstore> getBookstoreById(@PathVariable Long id) {
        return ResponseEntity.ok(bookstoreService.getBookstoreById(id));
    }

    @PostMapping("/addBookstore")
    public ResponseEntity<Bookstore> addBookstore(@RequestBody Bookstore bookstore) {
        return ResponseEntity.ok(bookstoreService.createBookstore(bookstore));
    }
}
