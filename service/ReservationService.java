package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.model.Reservation;
import com.library.management.model.User;
import com.library.management.repository.BookRepository;
import com.library.management.repository.ReservationRepository;
import com.library.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              UserRepository userRepository,
                              BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void reserveBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailableCopies() > 0) {
            throw new RuntimeException("Book is available. No need to reserve it.");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDateTime.now());

        reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reservationRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reservationRepository.findByUser(user);
    }
}
