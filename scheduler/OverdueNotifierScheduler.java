package com.library.management.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OverdueNotifierScheduler {

    @Scheduled(cron = "0 0 8 * * *") // Every day at 8 AM
    public void notifyOverdueBorrowers() {
        System.out.println("Running scheduled task: Notify overdue borrowers");
    }
}