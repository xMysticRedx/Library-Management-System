package com.library.management.controller;

import com.library.management.model.User;
import com.library.management.service.ReservationService;
import com.library.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibraryController {

    private final UserService userService;
    private final ReservationService reservationService;

    @Autowired
    public LibraryController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping("/my-library")
    public String myLibrary(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());

        model.addAttribute("borrowedBooks", user.getBorrowedBooks());
        model.addAttribute("reservedBooks", reservationService.getReservationsByUserId(user.getId()));

        return "my-library";
    }
}
