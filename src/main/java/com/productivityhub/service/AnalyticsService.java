package com.productivityhub.service;

import com.productivityhub.model.Analytics;
import com.productivityhub.model.Task;

import java.util.List;

public class AnalyticsService {
    private DataService dataService;

    public AnalyticsService(DataService dataService) {
        this.dataService = dataService;
    }

    public Analytics getAnalytics() {
        List<Task> tasks = dataService.getAllTasks();
        Analytics analytics = new Analytics();
        analytics.calculateFromTasks(tasks);
        return analytics;
    }
}

