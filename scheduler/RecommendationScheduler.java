package com.library.management.scheduler;

import com.library.management.service.RecommendationService;
import com.library.management.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecommendationScheduler {

    private final RecommendationService recommendationService;
    private final UserService userService;

    public RecommendationScheduler(RecommendationService recommendationService, UserService userService) {
        this.recommendationService = recommendationService;
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void fetchDailyUserRecommendations() {
        System.out.println("📚 Running daily recommendation fetch...");

        userService.getAllUsers().forEach(user -> {
            try {
                List<String> recommendations = recommendationService.getRecommendationsByUser(user.getId());
                System.out.printf("🔄 Fetched %d recs for %s%n", recommendations.size(), user.getUsername());

            } catch (Exception e) {
                System.err.printf("⚠️ Failed to fetch recs for %s: %s%n", user.getUsername(), e.getMessage());
            }
        });
    }
}