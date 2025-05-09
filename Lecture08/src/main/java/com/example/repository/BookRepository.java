package com.example.repository;

import com.example.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    public Optional<Book> findByName(String name);
    @Query(value = "select * from Book b where b.author=?1", nativeQuery = true)
    public List<Book> findBookByAuthor(String author);
}
