package com.library.management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    private User user;

    @Setter
    @ManyToOne
    private Book book;

    @Setter
    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;

    public Reservation() {}

    public Reservation(User user, Book book, LocalDateTime reservationDate) {
        this.user = user;
        this.book = book;
        this.reservationDate = reservationDate;
    }
}