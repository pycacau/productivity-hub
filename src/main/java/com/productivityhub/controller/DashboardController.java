package com.productivityhub.controller;

import com.productivityhub.model.Analytics;
import com.productivityhub.service.AnalyticsService;
import com.productivityhub.service.DataService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DashboardController {
    private VBox view;
    private DataService dataService;
    private AnalyticsService analyticsService;

    public DashboardController(DataService dataService, AnalyticsService analyticsService) {
        this.dataService = dataService;
        this.analyticsService = analyticsService;
        createView();
    }

    private void createView() {
        view = new VBox(20);
        view.setPadding(new Insets(30));
        view.setStyle("-fx-background-color: #0f0f1e;");

        // Título
        Label title = new Label("Dashboard de Produtividade");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.WHITE);

        // Cards de estatísticas
        HBox statsRow = createStatsCards();
        
        // Seção de resumo
        VBox summarySection = createSummarySection();

        view.getChildren().addAll(title, statsRow, summarySection);
    }

    private HBox createStatsCards() {
        HBox statsRow = new HBox(20);
        statsRow.setAlignment(Pos.CENTER_LEFT);

        Analytics analytics = analyticsService.getAnalytics();

        // Card: Total de Tarefas
        VBox totalCard = createStatCard(
            "Total de Tarefas",
            String.valueOf(analytics.getTotalTasks()),
            "#4A90E2"
        );

        // Card: Tarefas Concluídas
        VBox completedCard = createStatCard(
            "Concluídas",
            String.valueOf(analytics.getCompletedTasks()),
            "#50C878"
        );

        // Card: Taxa de Conclusão
        VBox rateCard = createStatCard(
            "Taxa de Conclusão",
            String.format("%.1f%%", analytics.getCompletionRate()),
            "#FF6B6B"
        );

        // Card: Sequência de Dias
        VBox streakCard = createStatCard(
            "Sequência Atual",
            String.valueOf(analytics.getStreakDays()) + " dias",
            "#FFD93D"
        );

        statsRow.getChildren().addAll(totalCard, completedCard, rateCard, streakCard);
        return statsRow;
    }

    private VBox createStatCard(String title, String value, String color) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setPrefWidth(200);
        card.setPrefHeight(120);
        card.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 15;");
        card.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        titleLabel.setTextFill(Color.WHITE);

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 32));
        valueLabel.setTextFill(Color.WHITE);

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private VBox createSummarySection() {
        VBox section = new VBox(15);
        section.setPadding(new Insets(20));
        section.setStyle("-fx-background-color: #1a1a2e; -fx-background-radius: 15;");

        Label sectionTitle = new Label("Resumo Rápido");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.WHITE);

        Analytics analytics = analyticsService.getAnalytics();

        Label tasksInfo = new Label(
            String.format("• %d tarefas em progresso\n• %d tarefas pendentes\n• Média de tempo: %.1f min/tarefa",
                analytics.getInProgressTasks(),
                analytics.getTodoTasks(),
                analytics.getAverageCompletionTime())
        );
        tasksInfo.setFont(Font.font("System", 14));
        tasksInfo.setTextFill(Color.LIGHTGRAY);
        tasksInfo.setLineSpacing(5);

        section.getChildren().addAll(sectionTitle, tasksInfo);
        return section;
    }

    public VBox getView() {
        return view;
    }
}

