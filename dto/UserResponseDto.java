package com.library.management.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {
    // Getters and setters
    private String username;
    private String email;
    private String role;

    public UserResponseDto() {
    }

    public UserResponseDto(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}