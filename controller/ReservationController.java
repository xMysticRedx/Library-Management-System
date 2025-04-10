package com.library.management.controller;

import com.library.management.model.Reservation;
import com.library.management.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/add/{bookId}")
    public String reserveBook(@PathVariable Long bookId,
                              @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        reservationService.reserveBook(Long.valueOf(username), bookId);

        return "redirect:/books";
    }

    @GetMapping("/my")
    public String viewMyReservations(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();

        List<Reservation> reservations = reservationService.getReservationsByUsername(username);
        model.addAttribute("reservations", reservations);

        return "my-reservations";
    }
}