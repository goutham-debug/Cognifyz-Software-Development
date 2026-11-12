package com.cognifyz.level3.task5;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Task model for persistent storage with Gson.
 */
public class Task {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private int id;
    private String title;
    private String description;
    private Priority priority;
    private TaskStatus status;
    private String createdAt;

    // No-arg constructor for Gson deserialization
    public Task() {}

    public Task(int id, String title, String description, Priority priority, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority != null ? priority : Priority.MEDIUM;
        this.status = status != null ? status : TaskStatus.PENDING;
        this.createdAt = LocalDateTime.now().format(FORMATTER);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s | %s) - %s", id, title, priority, status, description);
    }
}
