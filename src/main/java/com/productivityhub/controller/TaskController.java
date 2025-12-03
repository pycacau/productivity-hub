package com.productivityhub.controller;

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

public class TaskController {
    private VBox view;
    private DataService dataService;
    private AnalyticsService analyticsService;
    private VBox tasksContainer;

    public TaskController(DataService dataService, AnalyticsService analyticsService) {
        this.dataService = dataService;
        this.analyticsService = analyticsService;
        createView();
    }

    private void createView() {
        view = new VBox(20);
        view.setPadding(new Insets(30));
        view.setStyle("-fx-background-color: #0f0f1e;");

        // Cabeçalho
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);

        Label title = new Label("Gerenciar Tarefas");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.WHITE);

        Button addButton = new Button("+ Nova Tarefa");
        addButton.setStyle("-fx-background-color: #4A90E2; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 10 20;");
        addButton.setFont(Font.font("System", FontWeight.BOLD, 14));
        addButton.setOnAction(e -> showAddTaskDialog());

        header.getChildren().addAll(title, addButton);
        HBox.setHgrow(title, Priority.ALWAYS);

        // Container de tarefas
        tasksContainer = new VBox(15);
        tasksContainer.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(tasksContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        view.getChildren().addAll(header, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        refreshTasks();
    }

    private void refreshTasks() {
        tasksContainer.getChildren().clear();
        List<Task> tasks = dataService.getAllTasks();

        if (tasks.isEmpty()) {
            Label emptyLabel = new Label("Nenhuma tarefa cadastrada. Clique em 'Nova Tarefa' para começar!");
            emptyLabel.setFont(Font.font("System", 16));
            emptyLabel.setTextFill(Color.GRAY);
            emptyLabel.setPadding(new Insets(50));
            tasksContainer.getChildren().add(emptyLabel);
        } else {
            for (Task task : tasks) {
                tasksContainer.getChildren().add(createTaskCard(task));
            }
        }
    }

    private HBox createTaskCard(Task task) {
        HBox card = new HBox(15);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #1a1a2e; -fx-background-radius: 10;");
        card.setAlignment(Pos.CENTER_LEFT);

        // Checkbox
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(task.getStatus() == Task.TaskStatus.COMPLETED);
        checkBox.setOnAction(e -> {
            if (checkBox.isSelected()) {
                task.complete();
            } else {
                task.setStatus(Task.TaskStatus.TODO);
                task.setCompletedAt(null);
            }
            dataService.updateTask(task);
            refreshTasks();
        });

        // Informações da tarefa
        VBox taskInfo = new VBox(5);
        taskInfo.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(task.getTitle());
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.WHITE);

        HBox details = new HBox(15);
        Label categoryLabel = new Label("📁 " + (task.getCategory() != null ? task.getCategory() : "Sem categoria"));
        categoryLabel.setFont(Font.font("System", 12));
        categoryLabel.setTextFill(Color.LIGHTGRAY);

        Label priorityLabel = new Label("⚡ " + task.getPriority().name());
        priorityLabel.setFont(Font.font("System", 12));
        priorityLabel.setTextFill(getPriorityColor(task.getPriority()));

        Label statusLabel = new Label("📊 " + getStatusText(task.getStatus()));
        statusLabel.setFont(Font.font("System", 12));
        statusLabel.setTextFill(Color.LIGHTGRAY);

        details.getChildren().addAll(categoryLabel, priorityLabel, statusLabel);

        if (task.getDescription() != null && !task.getDescription().isEmpty()) {
            Label descLabel = new Label(task.getDescription());
            descLabel.setFont(Font.font("System", 12));
            descLabel.setTextFill(Color.GRAY);
            descLabel.setWrapText(true);
            taskInfo.getChildren().add(descLabel);
        }

        taskInfo.getChildren().addAll(titleLabel, details);

        // Botões de ação
        HBox actions = new HBox(10);
        Button editBtn = new Button("Editar");
        editBtn.setStyle("-fx-background-color: #4A90E2; -fx-text-fill: white; -fx-background-radius: 5;");
        editBtn.setOnAction(e -> showEditTaskDialog(task));

        Button deleteBtn = new Button("Excluir");
        deleteBtn.setStyle("-fx-background-color: #FF6B6B; -fx-text-fill: white; -fx-background-radius: 5;");
        deleteBtn.setOnAction(e -> {
            dataService.deleteTask(task.getId());
            refreshTasks();
        });

        actions.getChildren().addAll(editBtn, deleteBtn);

        card.getChildren().addAll(checkBox, taskInfo, actions);
        HBox.setHgrow(taskInfo, Priority.ALWAYS);

        return card;
    }

    private Color getPriorityColor(Task.TaskPriority priority) {
        switch (priority) {
            case URGENT: return Color.web("#FF6B6B");
            case HIGH: return Color.web("#FFA500");
            case MEDIUM: return Color.web("#FFD93D");
            case LOW: return Color.web("#50C878");
            default: return Color.LIGHTGRAY;
        }
    }

    private String getStatusText(Task.TaskStatus status) {
        switch (status) {
            case TODO: return "A Fazer";
            case IN_PROGRESS: return "Em Progresso";
            case COMPLETED: return "Concluída";
            case CANCELLED: return "Cancelada";
            default: return "Desconhecido";
        }
    }

    private void showAddTaskDialog() {
        showTaskDialog(null);
    }

    private void showEditTaskDialog(Task task) {
        showTaskDialog(task);
    }

    private void showTaskDialog(Task existingTask) {
        Dialog<Task> dialog = new Dialog<>();
        dialog.setTitle(existingTask == null ? "Nova Tarefa" : "Editar Tarefa");
        dialog.setHeaderText(existingTask == null ? "Criar uma nova tarefa" : "Editar tarefa existente");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField titleField = new TextField();
        titleField.setPromptText("Título da tarefa");
        if (existingTask != null) titleField.setText(existingTask.getTitle());

        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Descrição");
        descriptionField.setPrefRowCount(3);
        if (existingTask != null) descriptionField.setText(existingTask.getDescription());

        ComboBox<Task.TaskPriority> priorityCombo = new ComboBox<>();
        priorityCombo.getItems().addAll(Task.TaskPriority.values());
        priorityCombo.setValue(existingTask != null ? existingTask.getPriority() : Task.TaskPriority.MEDIUM);

        TextField categoryField = new TextField();
        categoryField.setPromptText("Categoria");
        if (existingTask != null) categoryField.setText(existingTask.getCategory());

        DatePicker dueDatePicker = new DatePicker();
        if (existingTask != null && existingTask.getDueDate() != null) {
            dueDatePicker.setValue(existingTask.getDueDate());
        }

        grid.add(new Label("Título:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Descrição:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Prioridade:"), 0, 2);
        grid.add(priorityCombo, 1, 2);
        grid.add(new Label("Categoria:"), 0, 3);
        grid.add(categoryField, 1, 3);
        grid.add(new Label("Data de Vencimento:"), 0, 4);
        grid.add(dueDatePicker, 1, 4);

        dialog.getDialogPane().setContent(grid);

        ButtonType saveButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                Task task = existingTask != null ? existingTask : new Task();
                task.setTitle(titleField.getText());
                task.setDescription(descriptionField.getText());
                task.setPriority(priorityCombo.getValue());
                task.setCategory(categoryField.getText());
                if (dueDatePicker.getValue() != null) {
                    task.setDueDate(dueDatePicker.getValue());
                }

                if (existingTask == null) {
                    dataService.addTask(task);
                } else {
                    dataService.updateTask(task);
                }
                refreshTasks();
            }
            return null;
        });

        dialog.showAndWait();
    }

    public VBox getView() {
        return view;
    }
}

