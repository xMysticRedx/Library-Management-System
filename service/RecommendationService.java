package com.library.management.service;

import com.library.management.repository.GenrePreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@Service
public class RecommendationService {

    private final RestTemplate restTemplate;

    private final String RECOMMENDATION_SERVICE_URL = "http://localhost:8081/recommendations";

    @Autowired
    public RecommendationService(RestTemplate restTemplate, GenrePreferenceRepository genrePreferenceRepository) {
        this.restTemplate = restTemplate;
    }

    public List<String> getRecommendationsByUser(Long userId) {
        String url = RECOMMENDATION_SERVICE_URL + "/user/" + userId;
        ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<String>>() {}
        );
        return response.getBody();
    }

    public List<String> getRecommendationsByGenre(String genre) {
        String url = RECOMMENDATION_SERVICE_URL + "/genre/" + genre;
        ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<String>>() {}
        );
        return response.getBody();
    }

    public void deletePreference(Long id) {
        String url = RECOMMENDATION_SERVICE_URL + "/" + id;
        System.out.println("Calling delete on: " + url); // <-- Add this for debugging
        restTemplate.delete(url);
    }
}