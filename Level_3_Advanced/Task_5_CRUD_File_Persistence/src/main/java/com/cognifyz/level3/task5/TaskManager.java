package com.cognifyz.level3.task5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Manages Tasks with persistent storage synchronization.
 * Every modifying operation (create, update, delete) triggers automatic persistence.
 */
public class TaskManager {

    private final TaskStorage storage;
    private final List<Task> tasks;
    private int nextId;

    public TaskManager(TaskStorage storage) {
        this.storage = storage;
        this.tasks = new ArrayList<>(storage.loadTasks());
        this.nextId = computeNextId();
    }

    private int computeNextId() {
        return tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * CREATE: Adds new task and automatically saves to JSON.
     */
    public Task createTask(String title, String description, Priority priority, TaskStatus status) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be blank.");
        }
        Task task = new Task(nextId++, title.trim(), description != null ? description.trim() : "", priority, status);
        tasks.add(task);
        storage.saveTasks(tasks);
        return task;
    }

    /**
     * READ: Returns unmodifiable list of tasks.
     */
    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * READ: Finds task by ID.
     */
    public Optional<Task> getTaskById(int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst();
    }

    /**
     * UPDATE: Updates task details and triggers automatic save.
     */
    public boolean updateTask(int id, String newTitle, String newDesc, Priority newPriority, TaskStatus newStatus) {
        Optional<Task> opt = getTaskById(id);
        if (opt.isEmpty()) {
            return false;
        }

        Task task = opt.get();
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            task.setTitle(newTitle.trim());
        }
        if (newDesc != null) {
            task.setDescription(newDesc.trim());
        }
        if (newPriority != null) {
            task.setPriority(newPriority);
        }
        if (newStatus != null) {
            task.setStatus(newStatus);
        }

        storage.saveTasks(tasks);
        return true;
    }

    /**
     * DELETE: Deletes task by ID and automatically saves changes.
     */
    public boolean deleteTask(int id) {
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) {
            storage.saveTasks(tasks);
        }
        return removed;
    }

    /**
     * SEARCH: Filters tasks matching title or description keyword.
     */
    public List<Task> searchTasks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllTasks();
        }
        String lower = query.trim().toLowerCase();
        return tasks.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(lower) ||
                             t.getDescription().toLowerCase().contains(lower))
                .toList();
    }

    /**
     * FILTER: Returns tasks matching given status.
     */
    public List<Task> filterByStatus(TaskStatus status) {
        return tasks.stream()
                .filter(t -> t.getStatus() == status)
                .toList();
    }

    public int size() {
        return tasks.size();
    }

    public TaskStorage getStorage() {
        return storage;
    }
}
