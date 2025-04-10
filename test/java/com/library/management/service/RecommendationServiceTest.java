package com.library.management.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationServiceTest {

    private RestTemplate restTemplate;
    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        recommendationService = new RecommendationService(restTemplate, null);
    }

    @Test
    void testDeletePreference_success() {
        Long id = 1L;
        String url = "http://localhost:8081/recommendations/" + id;

        recommendationService.deletePreference(id);

        verify(restTemplate, times(1)).delete(url);
    }

    @Test
    void testDeletePreference_notFound() {
        Long id = 99L;
        String url = "http://localhost:8081/recommendations/" + id;

        doThrow(new RuntimeException("Preference with ID " + id + " not found"))
                .when(restTemplate).delete(anyString());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                recommendationService.deletePreference(id)
        );

        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testGetRecommendationsByUser() {
        Long userId = 1L;
        String url = "http://localhost:8081/recommendations/user/" + userId;
        List<String> expected = Arrays.asList("Book A", "Book B");
        ResponseEntity<List<String>> response = new ResponseEntity<>(expected, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                eq(HttpEntity.EMPTY),
                any(ParameterizedTypeReference.class))
        ).thenReturn(response);

        List<String> result = recommendationService.getRecommendationsByUser(userId);
        assertEquals(expected, result);
    }

    @Test
    void testGetRecommendationsByGenre() {
        String genre = "Fantasy";
        String url = "http://localhost:8081/recommendations/genre/" + genre;
        List<String> expected = Arrays.asList("Book X", "Book Y");
        ResponseEntity<List<String>> response = new ResponseEntity<>(expected, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                eq(HttpEntity.EMPTY),
                any(ParameterizedTypeReference.class))
        ).thenReturn(response);

        List<String> result = recommendationService.getRecommendationsByGenre(genre);
        assertEquals(expected, result);
    }
}