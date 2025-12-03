package com.productivityhub.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private String id;
    private String name;
    private String description;
    private String color;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
    private int progress;
    private LocalDateTime createdAt;
    private List<String> taskIds;

    public enum ProjectStatus {
        PLANNING, ACTIVE, ON_HOLD, COMPLETED, CANCELLED
    }

    public Project() {
        this.taskIds = new ArrayList<>();
        this.status = ProjectStatus.PLANNING;
        this.progress = 0;
        this.createdAt = LocalDateTime.now();
        this.color = "#4A90E2";
    }

    public Project(String name, String description) {
        this();
        this.id = generateId();
        this.name = name;
        this.description = description;
    }

    private String generateId() {
        return "PROJ-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }

    public void addTask(String taskId) {
        if (!taskIds.contains(taskId)) {
            taskIds.add(taskId);
        }
    }

    public void removeTask(String taskId) {
        taskIds.remove(taskId);
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public ProjectStatus getStatus() { return status; }
    public void setStatus(ProjectStatus status) { this.status = status; }

    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<String> getTaskIds() { return taskIds; }
    public void setTaskIds(List<String> taskIds) { this.taskIds = taskIds; }
}

