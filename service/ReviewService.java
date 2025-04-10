package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.model.Review;
import com.library.management.model.User;
import com.library.management.repository.BookRepository;
import com.library.management.repository.ReviewRepository;
import com.library.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository,
                         BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public Long addReview(Long userId, Long bookId, int rating, String comment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Review review = new Review(user, book, rating, comment);
        Review savedReview = reviewRepository.save(review);

        return savedReview.getId();  // ✅ Returns only the ID
    }

    public List<Review> getReviewsForBook(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }
}