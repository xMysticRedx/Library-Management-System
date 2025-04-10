package com.library.management.controller;

import com.library.management.dto.UserRegisterDto;
import com.library.management.dto.UserResponseDto;
import com.library.management.model.Role;
import com.library.management.model.User;
import com.library.management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterDto userDto) {
        System.out.println(">>> REGISTER endpoint called with: " + userDto.getUsername());

        if (userService.usernameExists(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        User user = new User(
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getPassword(),
                Role.USER // ✅ Use enum, not string
        );

        userService.registerUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserProfile(@PathVariable Long id) {
        UserResponseDto profile = userService.getUserProfile(id);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserProfile(@PathVariable Long id,
                                                    @Valid @RequestBody UserResponseDto updated) {
        userService.updateUserProfile(id, updated);
        return ResponseEntity.ok("Profile updated successfully");
    }

    @PostMapping("/role")
    public ResponseEntity<String> updateUserRole(@RequestParam Long userId, @RequestParam String role) {
        userService.updateUserRole(userId, role);
        return ResponseEntity.ok("User role updated");
    }
}
