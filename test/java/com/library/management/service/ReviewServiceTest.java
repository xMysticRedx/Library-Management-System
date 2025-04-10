package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.model.Review;
import com.library.management.model.User;
import com.library.management.repository.BookRepository;
import com.library.management.repository.ReviewRepository;
import com.library.management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        reviewRepository = mock(ReviewRepository.class);
        userRepository = mock(UserRepository.class);
        bookRepository = mock(BookRepository.class);
        reviewService = new ReviewService(reviewRepository, userRepository, bookRepository);
    }

    @Test
    void testAddReview_success() {
        User user = new User();
        user.setId(1L);
        Book book = new Book();
        book.setId(2L);
        Review savedReview = new Review(user, book, 5, "Excellent!");
        savedReview.setId(10L); // Simulate saved review ID

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        Long reviewId = reviewService.addReview(1L, 2L, 5, "Excellent!");
        assertEquals(10L, reviewId);
    }

    @Test
    void testAddReview_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                reviewService.addReview(1L, 2L, 4, "Nice read")
        );

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testAddReview_bookNotFound() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                reviewService.addReview(1L, 2L, 4, "Good stuff")
        );

        assertEquals("Book not found", ex.getMessage());
    }
}