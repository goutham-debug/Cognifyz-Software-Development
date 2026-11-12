package com.cognifyz.level3.task5;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles persistent storage of tasks in JSON format using Google Gson.
 * Features automatic file creation, atomic writes, backup of corrupted files,
 * and comprehensive error handling.
 */
public class TaskStorage {

    private final Path filePath;
    private final Gson gson;
    private static final Type TASK_LIST_TYPE = new TypeToken<ArrayList<Task>>() {}.getType();

    public TaskStorage(String relativeOrAbsolutePath) {
        this.filePath = Paths.get(relativeOrAbsolutePath);
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Loads tasks from the JSON storage file.
     * Returns an empty list if file does not exist.
     * If corrupted, backs up file and returns empty list.
     */
    public List<Task> loadTasks() {
        if (!Files.exists(filePath)) {
            System.out.println("[INFO] Storage file does not exist yet. Initializing new storage at: " + filePath.toAbsolutePath());
            return new ArrayList<>();
        }

        File file = filePath.toFile();
        if (file.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            List<Task> tasks = gson.fromJson(reader, TASK_LIST_TYPE);
            if (tasks == null) {
                return new ArrayList<>();
            }
            System.out.printf("[INFO] Successfully loaded %d task(s) from %s%n", tasks.size(), filePath.getFileName());
            return tasks;
        } catch (JsonSyntaxException e) {
            System.err.println("[ERROR] Corrupted JSON detected in " + filePath.toAbsolutePath());
            handleCorruptedFile();
            return new ArrayList<>();
        } catch (IOException e) {
            System.err.println("[ERROR] I/O error reading task storage file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Saves tasks list to JSON file atomically.
     */
    public synchronized boolean saveTasks(List<Task> tasks) {
        try {
            // Ensure parent directory exists
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            // Write to a temporary file first then replace atomically
            Path tempFile = Paths.get(filePath.toString() + ".tmp");
            try (FileWriter writer = new FileWriter(tempFile.toFile())) {
                gson.toJson(tasks, writer);
            }

            Files.move(tempFile, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            return true;
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to save tasks to " + filePath.toAbsolutePath() + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Creates a timestamped backup copy when JSON corruption is detected.
     */
    private void handleCorruptedFile() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path backupPath = Paths.get(filePath.toString() + ".corrupt_" + timestamp + ".bak");
            Files.copy(filePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            System.err.println("[RECOVERY] Corrupted file backed up safely to: " + backupPath.toAbsolutePath());
            System.err.println("[RECOVERY] Starting with clean in-memory task database.");
        } catch (IOException e) {
            System.err.println("[ERROR] Could not create backup copy of corrupted file: " + e.getMessage());
        }
    }

    public Path getFilePath() {
        return filePath;
    }
}
