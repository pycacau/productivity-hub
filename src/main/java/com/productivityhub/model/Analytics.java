package com.productivityhub.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Analytics {
    private int totalTasks;
    private int completedTasks;
    private int inProgressTasks;
    private int todoTasks;
    private double completionRate;
    private int totalEstimatedMinutes;
    private int totalActualMinutes;
    private Map<String, Integer> tasksByCategory;
    private Map<String, Integer> tasksByPriority;
    private Map<LocalDate, Integer> tasksCompletedByDate;
    private double averageCompletionTime;
    private int streakDays;
    private LocalDate lastActivityDate;

    public Analytics() {
        this.tasksByCategory = new HashMap<>();
        this.tasksByPriority = new HashMap<>();
        this.tasksCompletedByDate = new HashMap<>();
    }

    public void calculateFromTasks(java.util.List<Task> tasks) {
        reset();
        
        for (Task task : tasks) {
            totalTasks++;
            
            switch (task.getStatus()) {
                case COMPLETED:
                    completedTasks++;
                    if (task.getCompletedAt() != null) {
                        LocalDate date = task.getCompletedAt().toLocalDate();
                        tasksCompletedByDate.put(date, 
                            tasksCompletedByDate.getOrDefault(date, 0) + 1);
                    }
                    if (task.getActualMinutes() > 0) {
                        totalActualMinutes += task.getActualMinutes();
                    }
                    break;
                case IN_PROGRESS:
                    inProgressTasks++;
                    break;
                case TODO:
                    todoTasks++;
                    break;
            }
            
            if (task.getEstimatedMinutes() > 0) {
                totalEstimatedMinutes += task.getEstimatedMinutes();
            }
            
            if (task.getCategory() != null && !task.getCategory().isEmpty()) {
                tasksByCategory.put(task.getCategory(),
                    tasksByCategory.getOrDefault(task.getCategory(), 0) + 1);
            }
            
            if (task.getPriority() != null) {
                String priority = task.getPriority().name();
                tasksByPriority.put(priority,
                    tasksByPriority.getOrDefault(priority, 0) + 1);
            }
        }
        
        if (totalTasks > 0) {
            completionRate = (double) completedTasks / totalTasks * 100;
        }
        
        if (completedTasks > 0) {
            averageCompletionTime = (double) totalActualMinutes / completedTasks;
        }
        
        calculateStreak();
    }

    private void reset() {
        totalTasks = 0;
        completedTasks = 0;
        inProgressTasks = 0;
        todoTasks = 0;
        completionRate = 0;
        totalEstimatedMinutes = 0;
        totalActualMinutes = 0;
        tasksByCategory.clear();
        tasksByPriority.clear();
        tasksCompletedByDate.clear();
        averageCompletionTime = 0;
    }

    private void calculateStreak() {
        streakDays = 0;
        LocalDate today = LocalDate.now();
        LocalDate checkDate = today;
        
        while (tasksCompletedByDate.containsKey(checkDate) && 
               tasksCompletedByDate.get(checkDate) > 0) {
            streakDays++;
            checkDate = checkDate.minusDays(1);
        }
        
        if (!tasksCompletedByDate.isEmpty()) {
            lastActivityDate = tasksCompletedByDate.keySet().stream()
                .max(LocalDate::compareTo)
                .orElse(null);
        }
    }

    // Getters
    public int getTotalTasks() { return totalTasks; }
    public int getCompletedTasks() { return completedTasks; }
    public int getInProgressTasks() { return inProgressTasks; }
    public int getTodoTasks() { return todoTasks; }
    public double getCompletionRate() { return completionRate; }
    public int getTotalEstimatedMinutes() { return totalEstimatedMinutes; }
    public int getTotalActualMinutes() { return totalActualMinutes; }
    public Map<String, Integer> getTasksByCategory() { return tasksByCategory; }
    public Map<String, Integer> getTasksByPriority() { return tasksByPriority; }
    public Map<LocalDate, Integer> getTasksCompletedByDate() { return tasksCompletedByDate; }
    public double getAverageCompletionTime() { return averageCompletionTime; }
    public int getStreakDays() { return streakDays; }
    public LocalDate getLastActivityDate() { return lastActivityDate; }
}

