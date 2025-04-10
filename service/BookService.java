package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setGenre(updatedBook.getGenre());
        book.setAvailableCopies(updatedBook.getAvailableCopies());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // --- New Features: Filtering + Pagination ---

    @Transactional(readOnly = true)
    public List<Book> getBooksByFilter(String genre, String author, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        if (genre != null && !genre.isEmpty() && author != null && !author.isEmpty()) {
            return bookRepository.findByGenreAndAuthor(genre, author, pageable).getContent();
        } else if (genre != null && !genre.isEmpty()) {
            return bookRepository.findByGenre(genre, pageable).getContent();
        } else if (author != null && !author.isEmpty()) {
            return bookRepository.findByAuthor(author, pageable).getContent();
        } else {
            return bookRepository.findAll(pageable).getContent();
        }
    }

    @Transactional(readOnly = true)
    public boolean hasNextPage(String genre, String author, int page, int pageSize) {
        Pageable nextPage = PageRequest.of(page + 1, pageSize);

        if (genre != null && !genre.isEmpty() && author != null && !author.isEmpty()) {
            return bookRepository.findByGenreAndAuthor(genre, author, nextPage).hasContent();
        } else if (genre != null && !genre.isEmpty()) {
            return bookRepository.findByGenre(genre, nextPage).hasContent();
        } else if (author != null && !author.isEmpty()) {
            return bookRepository.findByAuthor(author, nextPage).hasContent();
        } else {
            return bookRepository.findAll(nextPage).hasContent();
        }
    }

    @Transactional(readOnly = true)
    public List<String> getAllGenres() {
        return bookRepository.findDistinctGenres();
    }

    @Transactional(readOnly = true)
    public List<String> getAllAuthors() {
        return bookRepository.findDistinctAuthors();
    }
}