package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    void testGetAllBooks() {
        Book b1 = new Book();
        Book b2 = new Book();
        when(bookRepository.findAll()).thenReturn(Arrays.asList(b1, b2));

        List<Book> result = bookService.getAllBooks();
        assertEquals(2, result.size());
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        bookService.addBook(book);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBook() {
        Book originalBook = new Book();
        originalBook.setTitle("Old Title");
        originalBook.setAuthor("Author");
        originalBook.setGenre("Genre");
        originalBook.setAvailableCopies(5);

        Book updatedBook = new Book();
        updatedBook.setTitle("New Title");
        updatedBook.setAuthor("New Author");
        updatedBook.setGenre("New Genre");
        updatedBook.setAvailableCopies(10);

        // Correct mock setup
        when(bookRepository.findById(eq(1L))).thenReturn(Optional.of(originalBook));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0)); // 👈 return saved book

        Book result = bookService.updateBook(1L, updatedBook);

        verify(bookRepository, atLeastOnce()).findById(eq(1L));
        verify(bookRepository).save(originalBook);

        assertEquals("New Title", result.getTitle());
        assertEquals("New Author", result.getAuthor());
        assertEquals("New Genre", result.getGenre());
        assertEquals(10, result.getAvailableCopies());
    }

    @Test
    void testUpdateBook_bookNotFound() {
        when(bookRepository.findById(eq(99L))).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                bookService.updateBook(99L, new Book())
        );

        assertEquals("Book not found", ex.getMessage());
    }

    @Test
    void testDeleteBook() {
        bookService.deleteBook(1L);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void testGetBookById_found() {
        Book book = new Book();
        when(bookRepository.findById(eq(1L))).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testGetBookById_notFound() {
        when(bookRepository.findById(eq(99L))).thenReturn(Optional.empty());
        Optional<Book> result = bookService.getBookById(99L);
        assertFalse(result.isPresent());
    }
}