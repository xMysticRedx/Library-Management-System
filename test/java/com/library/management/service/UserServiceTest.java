package com.library.management.service;

import com.library.management.model.Role;
import com.library.management.model.User;
import com.library.management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User("user1", "user1@mail.com", "pass1", Role.USER);
        User user2 = new User("user2", "user2@mail.com", "pass2", Role.USER);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById() {
        User user = new User("user", "user@mail.com", "pass", Role.USER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("user", result.get().getUsername());
    }

    @Test
    void testRegisterUser() {
        User user = new User("newUser", "email@mail.com", "plainPass", Role.USER);
        when(passwordEncoder.encode("plainPass")).thenReturn("encodedPass");

        userService.registerUser(user);

        assertEquals("encodedPass", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserRole_validRole() {
        User user = new User("john", "john@mail.com", "pass", Role.USER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.updateUserRole(1L, "ADMIN");

        assertEquals(Role.ADMIN, user.getRole());
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUserRole_invalidRole() {
        User user = new User("john", "john@mail.com", "pass", Role.USER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Exception exception = assertThrows(RuntimeException.class, () ->
                userService.updateUserRole(1L, "INVALID_ROLE")
        );

        assertTrue(exception.getMessage().contains("Invalid role"));
    }

    @Test
    void testUsernameExists_true() {
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new User()));
        assertTrue(userService.usernameExists("existingUser"));
    }

    @Test
    void testUsernameExists_false() {
        when(userRepository.findByUsername("nonexistent"))
                .thenReturn(Optional.empty());
        assertFalse(userService.usernameExists("nonexistent"));
    }
}