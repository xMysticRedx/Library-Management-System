package com.library.management.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    @GetMapping("/stats")
    public ResponseEntity<String> getSystemStats() {
        return ResponseEntity.ok("System is running smoothly.");
    }
}

