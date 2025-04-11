package com.example.service;

import com.example.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {
    private List<Book> books;

    public LibraryService(@Value("#{'${library.books}'.split(',')}") List<String> books) {
        this.books = books.stream().map(bookEntry -> {
            String[] parts = bookEntry.split("\\|");
            return new Book(parts[0], parts[1]);
        }).collect(Collectors.toList());
    }

    public List<Book> getAvailableBooks() {
        return books.stream().filter(book -> !book.isReserved()).collect(Collectors.toList());
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
    }

    public boolean reserveBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isReserved()) {
                book.setReserved(true);
                return true;
            }
        }
        return false;
    }
}
