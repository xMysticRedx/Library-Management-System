package com.library.management.controller;

import com.library.management.repository.GenrePreferenceRepository;
import com.library.management.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationRestController {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private GenrePreferenceRepository genrePreferenceRepository;

    // GET: /api/recommendations/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<String>> getUserRecommendations(@PathVariable Long userId) {
        List<String> recommendations = recommendationService.getRecommendationsByUser(userId);
        return ResponseEntity.ok(recommendations);
    }

    // GET: /api/recommendations/genre/{genre}
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<String>> getGenreRecommendations(@PathVariable String genre) {
        List<String> recommendations = recommendationService.getRecommendationsByGenre(genre);
        return ResponseEntity.ok(recommendations);
    }

    // DELETE: /api/recommendations/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenrePreference(@PathVariable Long id) {
        return genrePreferenceRepository.findById(id)
                .map(preference -> {
                    genrePreferenceRepository.delete(preference);
                    return ResponseEntity.ok("Preference deleted successfully.");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}