package com.library.management.controller;

import com.library.management.model.Book;
import com.library.management.model.User;
import com.library.management.service.BookService;
import com.library.management.service.ReservationService;
import com.library.management.service.ReviewService;
import com.library.management.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class UserBookController {

    private final BookService bookService;
    private final ReviewService reviewService;
    private final ReservationService reservationService;
    private final UserService userService;

    public UserBookController(BookService bookService,
                              ReviewService reviewService,
                              ReservationService reservationService,
                              UserService userService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping
    public String viewAvailableBooks(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        int pageSize = 6;

        List<Book> books = bookService.getBooksByFilter(genre, author, page, pageSize);
        books.forEach(book -> book.setReviews(reviewService.getReviewsForBook(book.getId())));

        model.addAttribute("books", books);
        model.addAttribute("genres", bookService.getAllGenres());
        model.addAttribute("authors", bookService.getAllAuthors());
        model.addAttribute("page", page);
        model.addAttribute("hasNext", bookService.hasNextPage(genre, author, page, pageSize));

        return "browsing-books";
    }

    @PostMapping("/reserve/{id}")
    public String reserveBook(@PathVariable Long id, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        Book book = bookService.getBookById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (book.getAvailableCopies() > 0) {
            // Redirect with message that book is available and doesn’t need reservation
            System.out.println("⚠️ Book has available copies; reservation skipped.");
            return "redirect:/books";
        }

        reservationService.reserveBook(user.getId(), id);
        System.out.println("✅ Reserved book with ID: " + id + " for user: " + user.getUsername());
        return "redirect:/books";
    }

    @GetMapping("/my-library")
    public String myLibrary(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());

        model.addAttribute("borrowedBooks", user.getBorrowedBooks()); // Assuming getBorrowedBooks() exists
        model.addAttribute("reservedBooks", reservationService.getReservationsByUserId(user.getId()));

        return "my-library";
    }
}