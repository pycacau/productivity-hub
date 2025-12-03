package com.productivityhub.controller;

import com.productivityhub.model.Analytics;
import com.productivityhub.model.Project;
import com.productivityhub.model.Task;
import com.productivityhub.service.AnalyticsService;
import com.productivityhub.service.DataService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.util.List;

public class MainController {
    private BorderPane root;
    private DataService dataService;
    private AnalyticsService analyticsService;
    private TaskController taskController;
    private ProjectController projectController;
    private DashboardController dashboardController;
    
    public MainController() {
        this.dataService = new DataService();
        this.analyticsService = new AnalyticsService(dataService);
        this.root = new BorderPane();
        
        createUI();
    }
    
    private void createUI() {
        // Top Menu Bar
        HBox topBar = createTopBar();
        root.setTop(topBar);
        
        // Center - Dashboard inicial
        dashboardController = new DashboardController(dataService, analyticsService);
        root.setCenter(dashboardController.getView());
    }
    
    private HBox createTopBar() {
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(20));
        topBar.setStyle("-fx-background-color: #1a1a2e;");
        topBar.setAlignment(Pos.CENTER_LEFT);
        
        // Logo/Título
        Label title = new Label("ProductivityHub");
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);
        
        // Botões de navegação
        Button dashboardBtn = createNavButton("Dashboard", true);
        Button tasksBtn = createNavButton("Tarefas", false);
        Button projectsBtn = createNavButton("Projetos", false);
        Button analyticsBtn = createNavButton("Analytics", false);
        
        dashboardBtn.setOnAction(e -> showDashboard());
        tasksBtn.setOnAction(e -> showTasks());
        projectsBtn.setOnAction(e -> showProjects());
        analyticsBtn.setOnAction(e -> showAnalytics());
        
        HBox navButtons = new HBox(10);
        navButtons.getChildren().addAll(dashboardBtn, tasksBtn, projectsBtn, analyticsBtn);
        
        topBar.getChildren().addAll(title, navButtons);
        HBox.setHgrow(navButtons, Priority.ALWAYS);
        
        return topBar;
    }
    
    private Button createNavButton(String text, boolean active) {
        Button btn = new Button(text);
        btn.setPrefHeight(35);
        btn.setPrefWidth(120);
        if (active) {
            btn.setStyle("-fx-background-color: #4A90E2; -fx-text-fill: white; -fx-background-radius: 5;");
        } else {
            btn.setStyle("-fx-background-color: #2d2d44; -fx-text-fill: #b0b0b0; -fx-background-radius: 5;");
        }
        btn.setOnMouseEntered(e -> {
            if (!btn.getStyle().contains("#4A90E2")) {
                btn.setStyle("-fx-background-color: #3d3d5c; -fx-text-fill: white; -fx-background-radius: 5;");
            }
        });
        btn.setOnMouseExited(e -> {
            if (!btn.getStyle().contains("#4A90E2")) {
                btn.setStyle("-fx-background-color: #2d2d44; -fx-text-fill: #b0b0b0; -fx-background-radius: 5;");
            }
        });
        return btn;
    }
    
    private void showDashboard() {
        dashboardController = new DashboardController(dataService, analyticsService);
        root.setCenter(dashboardController.getView());
    }
    
    private void showTasks() {
        if (taskController == null) {
            taskController = new TaskController(dataService, analyticsService);
        }
        root.setCenter(taskController.getView());
    }
    
    private void showProjects() {
        if (projectController == null) {
            projectController = new ProjectController(dataService, analyticsService);
        }
        root.setCenter(projectController.getView());
    }
    
    private void showAnalytics() {
        AnalyticsController analyticsController = new AnalyticsController(dataService, analyticsService);
        root.setCenter(analyticsController.getView());
    }
    
    public BorderPane getRoot() {
        return root;
    }
}

