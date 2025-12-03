package com.productivityhub.controller;

import com.productivityhub.model.Project;
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

public class ProjectController {
    private VBox view;
    private DataService dataService;
    private AnalyticsService analyticsService;
    private VBox projectsContainer;

    public ProjectController(DataService dataService, AnalyticsService analyticsService) {
        this.dataService = dataService;
        this.analyticsService = analyticsService;
        createView();
    }

    private void createView() {
        view = new VBox(20);
        view.setPadding(new Insets(30));
        view.setStyle("-fx-background-color: #0f0f1e;");

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);

        Label title = new Label("Gerenciar Projetos");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.WHITE);

        Button addButton = new Button("+ Novo Projeto");
        addButton.setStyle("-fx-background-color: #4A90E2; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10 20;");
        addButton.setFont(Font.font("System", FontWeight.BOLD, 14));
        addButton.setOnAction(e -> showAddProjectDialog());

        header.getChildren().addAll(title, addButton);
        HBox.setHgrow(title, Priority.ALWAYS);

        projectsContainer = new VBox(15);
        projectsContainer.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(projectsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        view.getChildren().addAll(header, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        refreshProjects();
    }

    private void refreshProjects() {
        projectsContainer.getChildren().clear();
        List<Project> projects = dataService.getAllProjects();

        if (projects.isEmpty()) {
            Label emptyLabel = new Label("Nenhum projeto cadastrado. Clique em 'Novo Projeto' para começar!");
            emptyLabel.setFont(Font.font("System", 16));
            emptyLabel.setTextFill(Color.GRAY);
            emptyLabel.setPadding(new Insets(50));
            projectsContainer.getChildren().add(emptyLabel);
        } else {
            for (Project project : projects) {
                projectsContainer.getChildren().add(createProjectCard(project));
            }
        }
    }

    private VBox createProjectCard(Project project) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #1a1a2e; -fx-background-radius: 10;");

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        // Indicador de cor
        Region colorIndicator = new Region();
        colorIndicator.setPrefWidth(5);
        colorIndicator.setPrefHeight(40);
        colorIndicator.setStyle("-fx-background-color: " + project.getColor() + "; -fx-background-radius: 3;");

        VBox info = new VBox(5);
        Label nameLabel = new Label(project.getName());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        nameLabel.setTextFill(Color.WHITE);

        Label descLabel = new Label(project.getDescription() != null ? project.getDescription() : "Sem descrição");
        descLabel.setFont(Font.font("System", 12));
        descLabel.setTextFill(Color.LIGHTGRAY);

        info.getChildren().addAll(nameLabel, descLabel);

        HBox details = new HBox(20);
        Label statusLabel = new Label("Status: " + getStatusText(project.getStatus()));
        statusLabel.setFont(Font.font("System", 12));
        statusLabel.setTextFill(Color.LIGHTGRAY);

        Label progressLabel = new Label("Progresso: " + project.getProgress() + "%");
        progressLabel.setFont(Font.font("System", 12));
        progressLabel.setTextFill(Color.LIGHTGRAY);

        ProgressBar progressBar = new ProgressBar(project.getProgress() / 100.0);
        progressBar.setPrefWidth(200);
        progressBar.setStyle("-fx-accent: " + project.getColor() + ";");

        details.getChildren().addAll(statusLabel, progressLabel, progressBar);

        HBox actions = new HBox(10);
        Button editBtn = new Button("Editar");
        editBtn.setStyle("-fx-background-color: #4A90E2; -fx-text-fill: white; -fx-background-radius: 5;");
        editBtn.setOnAction(e -> showEditProjectDialog(project));

        Button deleteBtn = new Button("Excluir");
        deleteBtn.setStyle("-fx-background-color: #FF6B6B; -fx-text-fill: white; -fx-background-radius: 5;");
        deleteBtn.setOnAction(e -> {
            dataService.deleteProject(project.getId());
            refreshProjects();
        });

        actions.getChildren().addAll(editBtn, deleteBtn);

        header.getChildren().addAll(colorIndicator, info);
        HBox.setHgrow(info, Priority.ALWAYS);

        card.getChildren().addAll(header, details, actions);
        return card;
    }

    private String getStatusText(Project.ProjectStatus status) {
        switch (status) {
            case PLANNING: return "Planejamento";
            case ACTIVE: return "Ativo";
            case ON_HOLD: return "Em Pausa";
            case COMPLETED: return "Concluído";
            case CANCELLED: return "Cancelado";
            default: return "Desconhecido";
        }
    }

    private void showAddProjectDialog() {
        showProjectDialog(null);
    }

    private void showEditProjectDialog(Project project) {
        showProjectDialog(project);
    }

    private void showProjectDialog(Project existingProject) {
        Dialog<Project> dialog = new Dialog<>();
        dialog.setTitle(existingProject == null ? "Novo Projeto" : "Editar Projeto");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField nameField = new TextField();
        nameField.setPromptText("Nome do projeto");
        if (existingProject != null) nameField.setText(existingProject.getName());

        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Descrição");
        descriptionField.setPrefRowCount(3);
        if (existingProject != null) descriptionField.setText(existingProject.getDescription());

        ComboBox<Project.ProjectStatus> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll(Project.ProjectStatus.values());
        statusCombo.setValue(existingProject != null ? existingProject.getStatus() : Project.ProjectStatus.PLANNING);

        TextField colorField = new TextField();
        colorField.setPromptText("#4A90E2");
        if (existingProject != null) colorField.setText(existingProject.getColor());

        Spinner<Integer> progressSpinner = new Spinner<>(0, 100, existingProject != null ? existingProject.getProgress() : 0);
        progressSpinner.setEditable(true);

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Descrição:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Status:"), 0, 2);
        grid.add(statusCombo, 1, 2);
        grid.add(new Label("Cor (hex):"), 0, 3);
        grid.add(colorField, 1, 3);
        grid.add(new Label("Progresso (%):"), 0, 4);
        grid.add(progressSpinner, 1, 4);

        dialog.getDialogPane().setContent(grid);

        ButtonType saveButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                Project project = existingProject != null ? existingProject : new Project();
                project.setName(nameField.getText());
                project.setDescription(descriptionField.getText());
                project.setStatus(statusCombo.getValue());
                project.setColor(colorField.getText().isEmpty() ? "#4A90E2" : colorField.getText());
                project.setProgress(progressSpinner.getValue());

                if (existingProject == null) {
                    dataService.addProject(project);
                } else {
                    dataService.updateProject(project);
                }
                refreshProjects();
            }
            return null;
        });

        dialog.showAndWait();
    }

    public VBox getView() {
        return view;
    }
}

