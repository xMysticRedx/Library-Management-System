package com.library.management.controller;

import com.library.management.model.BorrowingRecord;
import com.library.management.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        borrowingService.borrowBook(userId, bookId);
        return ResponseEntity.ok("Book borrowed successfully");
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        borrowingService.returnBook(userId, bookId);
        return ResponseEntity.ok("Book returned successfully");
    }

    @GetMapping("/history/{userId}")
    public List<BorrowingRecord> getBorrowingHistory(@PathVariable Long userId) {
        return borrowingService.getBorrowingHistory(userId);
    }
}