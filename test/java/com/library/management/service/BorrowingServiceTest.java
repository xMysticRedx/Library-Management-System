package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.model.BorrowingRecord;
import com.library.management.model.User;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowingRecordRepository;
import com.library.management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowingServiceTest {

    private BorrowingRecordRepository borrowingRecordRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BorrowingService borrowingService;

    @BeforeEach
    void setUp() {
        borrowingRecordRepository = mock(BorrowingRecordRepository.class);
        bookRepository = mock(BookRepository.class);
        userRepository = mock(UserRepository.class);

        borrowingService = new BorrowingService(borrowingRecordRepository, userRepository, bookRepository);
    }

    @Test
    void testBorrowBook_success() {
        User user = new User();
        Book book = new Book();
        book.setAvailableCopies(2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));

        borrowingService.borrowBook(1L, 2L);

        verify(bookRepository).save(book);
        verify(borrowingRecordRepository).save(any(BorrowingRecord.class));
        assertEquals(1, book.getAvailableCopies());
    }

    @Test
    void testBorrowBook_noCopies() {
        User user = new User();
        Book book = new Book();
        book.setAvailableCopies(0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));

        Exception ex = assertThrows(RuntimeException.class, () -> borrowingService.borrowBook(1L, 2L));
        assertTrue(ex.getMessage().contains("No available copies"));
    }

    @Test
    void testReturnBook_success() {
        Book book = new Book();
        book.setAvailableCopies(1);

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);

        when(borrowingRecordRepository.findByUserIdAndBookIdAndReturnDateIsNull(1L, 2L)).thenReturn(Optional.of(record));

        borrowingService.returnBook(1L, 2L);

        verify(borrowingRecordRepository).save(record);
        verify(bookRepository).save(book);
        assertEquals(2, book.getAvailableCopies());
        assertNotNull(record.getReturnDate());
    }

    @Test
    void testReturnBook_noRecordFound() {
        when(borrowingRecordRepository.findByUserIdAndBookIdAndReturnDateIsNull(1L, 2L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> borrowingService.returnBook(1L, 2L));
        assertTrue(ex.getMessage().contains("No active borrowing record"));
    }

    @Test
    void testGetBorrowingHistory() {
        List<BorrowingRecord> mockHistory = List.of(new BorrowingRecord(), new BorrowingRecord());
        when(borrowingRecordRepository.findByUserId(1L)).thenReturn(mockHistory);

        List<BorrowingRecord> result = borrowingService.getBorrowingHistory(1L);

        assertEquals(2, result.size());
    }
}