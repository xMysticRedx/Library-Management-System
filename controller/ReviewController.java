package com.library.management.controller;

import com.library.management.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestParam Long userId,
                                            @RequestParam Long bookId,
                                            @RequestParam int rating,
                                            @RequestParam String comment) {
        Long reviewId = reviewService.addReview(userId, bookId, rating, comment);
        return ResponseEntity.ok("Review added successfully! ID: " + reviewId);
    }
}