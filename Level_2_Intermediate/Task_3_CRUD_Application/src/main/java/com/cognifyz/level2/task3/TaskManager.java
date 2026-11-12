package com.cognifyz.level2.task3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Manages in-memory CRUD operations, searches, and filters for Tasks.
 */
public class TaskManager {

    private final List<Task> taskList;
    private int nextId;

    public TaskManager() {
        this.taskList = new ArrayList<>();
        this.nextId = 1;
    }

    /**
     * CREATE: Adds a new task with a guaranteed unique incremental ID.
     */
    public Task createTask(String title, String description, Priority priority, TaskStatus status) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be blank.");
        }
        Task task = new Task(nextId++, title.trim(), description != null ? description.trim() : "", priority, status);
        taskList.add(task);
        return task;
    }

    /**
     * READ: Returns an unmodifiable view of all registered tasks.
     */
    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(taskList);
    }

    /**
     * READ: Finds a specific task by its unique identifier.
     */
    public Optional<Task> getTaskById(int id) {
        return taskList.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    /**
     * UPDATE: Updates editable task properties if found.
     */
    public boolean updateTask(int id, String newTitle, String newDescription, Priority newPriority, TaskStatus newStatus) {
        Optional<Task> optionalTask = getTaskById(id);
        if (optionalTask.isEmpty()) {
            return false;
        }

        Task task = optionalTask.get();
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            task.setTitle(newTitle.trim());
        }
        if (newDescription != null) {
            task.setDescription(newDescription.trim());
        }
        if (newPriority != null) {
            task.setPriority(newPriority);
        }
        if (newStatus != null) {
            task.setStatus(newStatus);
        }
        return true;
    }

    /**
     * DELETE: Removes a task by ID.
     */
    public boolean deleteTask(int id) {
        return taskList.removeIf(t -> t.getId() == id);
    }

    /**
     * SEARCH: Searches tasks matching a query in title or description.
     */
    public List<Task> searchTasks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllTasks();
        }
        String lower = query.trim().toLowerCase();
        return taskList.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(lower) ||
                             t.getDescription().toLowerCase().contains(lower))
                .toList();
    }

    /**
     * FILTER: Returns tasks matching a specific status.
     */
    public List<Task> filterByStatus(TaskStatus status) {
        return taskList.stream()
                .filter(t -> t.getStatus() == status)
                .toList();
    }

    /**
     * Returns total count of tasks.
     */
    public int size() {
        return taskList.size();
    }
}
