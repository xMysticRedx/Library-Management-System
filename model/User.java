package com.library.management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Username
    @Column(nullable = false, unique = true)
    private String username;

    // Email
    @Column(nullable = false, unique = true)
    private String email;

    // Password
    @Column(nullable = false)
    private String password;

    // Role
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Borrowed books (relationship to BorrowingRecord)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowingRecord> borrowingRecords = new ArrayList<>();

    public User() {
    }

    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public List<BorrowingRecord> getBorrowedBooks() {
        return this.borrowingRecords;
    }
}