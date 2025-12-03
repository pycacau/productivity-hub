package com.productivityhub.controller;

import com.productivityhub.model.Analytics;
import com.productivityhub.service.AnalyticsService;
import com.productivityhub.service.DataService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Map;

public class AnalyticsController {
    private VBox view;
    private DataService dataService;
    private AnalyticsService analyticsService;

    public AnalyticsController(DataService dataService, AnalyticsService analyticsService) {
        this.dataService = dataService;
        this.analyticsService = analyticsService;
        createView();
    }

    private void createView() {
        view = new VBox(20);
        view.setPadding(new Insets(30));
        view.setStyle("-fx-background-color: #0f0f1e;");

        Label title = new Label("Analytics e Estatísticas");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.WHITE);

        Analytics analytics = analyticsService.getAnalytics();

        // Gráfico de pizza - Tarefas por Status
        PieChart statusChart = createStatusChart(analytics);
        
        // Gráfico de barras - Tarefas por Categoria
        BarChart<String, Number> categoryChart = createCategoryChart(analytics);
        
        // Gráfico de barras - Tarefas por Prioridade
        BarChart<String, Number> priorityChart = createPriorityChart(analytics);

        HBox chartsRow1 = new HBox(20);
        chartsRow1.getChildren().addAll(statusChart, categoryChart);
        
        HBox chartsRow2 = new HBox(20);
        chartsRow2.getChildren().add(priorityChart);

        view.getChildren().addAll(title, chartsRow1, chartsRow2);
    }

    private PieChart createStatusChart(Analytics analytics) {
        PieChart chart = new PieChart();
        chart.setTitle("Tarefas por Status");
        chart.setPrefSize(400, 300);
        chart.setStyle("-fx-background-color: #1a1a2e; -fx-text-fill: white;");

        chart.getData().add(new PieChart.Data("Concluídas", analytics.getCompletedTasks()));
        chart.getData().add(new PieChart.Data("Em Progresso", analytics.getInProgressTasks()));
        chart.getData().add(new PieChart.Data("A Fazer", analytics.getTodoTasks()));

        return chart;
    }

    private BarChart<String, Number> createCategoryChart(Analytics analytics) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Tarefas por Categoria");
        chart.setPrefSize(400, 300);
        chart.setStyle("-fx-background-color: #1a1a2e;");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tarefas");

        for (Map.Entry<String, Integer> entry : analytics.getTasksByCategory().entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        chart.getData().add(series);
        return chart;
    }

    private BarChart<String, Number> createPriorityChart(Analytics analytics) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Tarefas por Prioridade");
        chart.setPrefSize(800, 300);
        chart.setStyle("-fx-background-color: #1a1a2e;");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tarefas");

        for (Map.Entry<String, Integer> entry : analytics.getTasksByPriority().entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        chart.getData().add(series);
        return chart;
    }

    public VBox getView() {
        return view;
    }
}

