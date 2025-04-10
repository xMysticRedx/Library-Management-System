package com.library.management.service;

import com.library.management.dto.UserResponseDto;
import com.library.management.model.Role;
import com.library.management.model.User;
import com.library.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserResponseDto getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDto(
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }

    public void updateUserProfile(Long id, UserResponseDto updated) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(updated.getUsername());
        user.setEmail(updated.getEmail());
        userRepository.save(user);
    }

    public void updateUserRole(Long userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            Role parsedRole = Role.valueOf(role.toUpperCase());
            user.setRole(parsedRole);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + role);
        }

        userRepository.save(user);
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}