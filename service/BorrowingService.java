package com.library.management.service;

import com.library.management.model.BorrowingRecord;
import com.library.management.model.Book;
import com.library.management.model.User;
import com.library.management.repository.BorrowingRecordRepository;
import com.library.management.repository.BookRepository;
import com.library.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BorrowingService(BorrowingRecordRepository borrowingRecordRepository,
                            UserRepository userRepository,
                            BookRepository bookRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<BorrowingRecord> getBorrowingHistory(Long userId) {
        return borrowingRecordRepository.findByUserId(userId);
    }

    public void borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No available copies of this book");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        BorrowingRecord record = new BorrowingRecord();
        record.setUser(user);
        record.setBook(book);
        record.setBorrowDate(LocalDate.now());
        borrowingRecordRepository.save(record);
    }

    public void returnBook(Long userId, Long bookId) {
        BorrowingRecord record = borrowingRecordRepository
                .findByUserIdAndBookIdAndReturnDateIsNull(userId, bookId)
                .orElseThrow(() -> new RuntimeException("No active borrowing record found"));

        record.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(record);

        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
    }
}