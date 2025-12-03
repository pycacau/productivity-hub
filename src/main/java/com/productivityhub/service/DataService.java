package com.productivityhub.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.productivityhub.model.Project;
import com.productivityhub.model.Task;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private static final String DATA_DIR = "data";
    private static final String TASKS_FILE = DATA_DIR + File.separator + "tasks.json";
    private static final String PROJECTS_FILE = DATA_DIR + File.separator + "projects.json";
    
    private Gson gson;
    private List<Task> tasks;
    private List<Project> projects;

    public DataService() {
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
        this.tasks = new ArrayList<>();
        this.projects = new ArrayList<>();
        ensureDataDirectory();
        loadData();
    }

    private void ensureDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void loadData() {
        loadTasks();
        loadProjects();
    }

    public void saveData() {
        saveTasks();
        saveProjects();
    }

    private void loadTasks() {
        try {
            File file = new File(TASKS_FILE);
            if (file.exists()) {
                Type listType = new TypeToken<List<Task>>(){}.getType();
                tasks = gson.fromJson(new FileReader(file), listType);
                if (tasks == null) {
                    tasks = new ArrayList<>();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar tarefas: " + e.getMessage());
            tasks = new ArrayList<>();
        }
    }

    private void saveTasks() {
        try {
            FileWriter writer = new FileWriter(TASKS_FILE);
            gson.toJson(tasks, writer);
            writer.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar tarefas: " + e.getMessage());
        }
    }

    private void loadProjects() {
        try {
            File file = new File(PROJECTS_FILE);
            if (file.exists()) {
                Type listType = new TypeToken<List<Project>>(){}.getType();
                projects = gson.fromJson(new FileReader(file), listType);
                if (projects == null) {
                    projects = new ArrayList<>();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar projetos: " + e.getMessage());
            projects = new ArrayList<>();
        }
    }

    private void saveProjects() {
        try {
            FileWriter writer = new FileWriter(PROJECTS_FILE);
            gson.toJson(projects, writer);
            writer.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar projetos: " + e.getMessage());
        }
    }

    // Task operations
    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    public void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(task.getId())) {
                tasks.set(i, task);
                saveTasks();
                return;
            }
        }
    }

    public void deleteTask(String taskId) {
        tasks.removeIf(t -> t.getId().equals(taskId));
        saveTasks();
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public Task getTaskById(String id) {
        return tasks.stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    // Project operations
    public void addProject(Project project) {
        projects.add(project);
        saveProjects();
    }

    public void updateProject(Project project) {
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getId().equals(project.getId())) {
                projects.set(i, project);
                saveProjects();
                return;
            }
        }
    }

    public void deleteProject(String projectId) {
        projects.removeIf(p -> p.getId().equals(projectId));
        // Remove tasks associated with this project
        tasks.removeIf(t -> projectId.equals(t.getProjectId()));
        saveProjects();
        saveTasks();
    }

    public List<Project> getAllProjects() {
        return new ArrayList<>(projects);
    }

    public Project getProjectById(String id) {
        return projects.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}

