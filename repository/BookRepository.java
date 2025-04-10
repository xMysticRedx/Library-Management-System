package com.library.management.repository;

import com.library.management.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByGenre(String genre, Pageable pageable);
    Page<Book> findByAuthor(String author, Pageable pageable);
    Page<Book> findByGenreAndAuthor(String genre, String author, Pageable pageable);

    @Query("SELECT DISTINCT b.genre FROM Book b")
    List<String> findDistinctGenres();

    @Query("SELECT DISTINCT b.author FROM Book b")
    List<String> findDistinctAuthors();
}