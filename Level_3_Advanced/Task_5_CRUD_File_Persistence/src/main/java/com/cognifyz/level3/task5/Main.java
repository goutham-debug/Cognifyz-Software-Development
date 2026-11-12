package com.cognifyz.level3.task5;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Cognifyz Technologies - Software Development Internship
 * Level 3: Advanced - Task 5: Persistent CRUD Task Manager
 *
 * Demonstrates file I/O, Google Gson JSON serialization, defensive error handling,
 * and data preservation across application sessions.
 *
 * @author Cognifyz Intern
 */
public class Main {

    private static final String DEFAULT_STORAGE_PATH = "data/tasks.json";

    private final TaskManager taskManager;
    private final Scanner scanner;

    public Main(String storagePath) {
        TaskStorage storage = new TaskStorage(storagePath);
        this.taskManager = new TaskManager(storage);
        this.scanner = new Scanner(System.in);

        // If newly created storage with 0 items, seed starter tasks
        if (taskManager.size() == 0) {
            seedInitialPersistentTasks();
        }
    }

    public static void main(String[] args) {
        String path = (args.length > 0) ? args[0] : DEFAULT_STORAGE_PATH;
        Main app = new Main(path);
        app.run();
    }

    private void seedInitialPersistentTasks() {
        System.out.println("[INFO] Seeding initial demonstration tasks for first run...");
        taskManager.createTask("Configure Maven POM", "Add Google Gson dependency for JSON file persistence.", Priority.HIGH, TaskStatus.COMPLETED);
        taskManager.createTask("Implement TaskStorage", "Implement JSON read/write routines and corruption backups.", Priority.HIGH, TaskStatus.COMPLETED);
        taskManager.createTask("Verify Session Persistence", "Restart application and ensure all tasks remain loaded.", Priority.MEDIUM, TaskStatus.IN_PROGRESS);
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntChoice(0, 7);
            switch (choice) {
                case 1 -> handleListAllTasks();
                case 2 -> handleCreateTask();
                case 3 -> handleUpdateTask();
                case 4 -> handleDeleteTask();
                case 5 -> handleSearchTasks();
                case 6 -> handleFilterByStatus();
                case 7 -> handleShowStorageInfo();
                case 0 -> {
                    running = false;
                    System.out.println("\nAll changes are safely persisted to disk. Exiting Task Manager. Goodbye!");
                }
            }
        }
    }

    private void printMenu() {
        System.out.println("\n========================================================");
        System.out.println("   COGNIFYZ TASK MANAGER (JSON FILE PERSISTENCE)        ");
        System.out.println("========================================================");
        System.out.println(" 1. View All Tasks");
        System.out.println(" 2. Add New Task (Auto-Saved)");
        System.out.println(" 3. Update Existing Task (Auto-Saved)");
        System.out.println(" 4. Delete Task (Auto-Saved)");
        System.out.println(" 5. Search Tasks by Keyword");
        System.out.println(" 6. Filter Tasks by Status");
        System.out.println(" 7. View Persistent Storage Diagnostics");
        System.out.println(" 0. Exit Application");
        System.out.println("========================================================");
    }

    private void handleListAllTasks() {
        List<Task> tasks = taskManager.getAllTasks();
        displayTaskTable(tasks, "ALL PERSISTED TASKS");
    }

    private void handleCreateTask() {
        System.out.println("\n--- ADD NEW TASK (AUTOMATIC PERSISTENCE) ---");
        String title = readNonEmptyString("Enter task title: ");
        System.out.print("Enter task description (optional): ");
        String desc = scanner.nextLine().trim();

        Priority priority = selectPriority();
        TaskStatus status = selectStatus();

        Task created = taskManager.createTask(title, desc, priority, status);
        System.out.printf("%n[+] Task #%d created and persisted to JSON successfully!%n", created.getId());
    }

    private void handleUpdateTask() {
        System.out.println("\n--- UPDATE TASK ---");
        handleListAllTasks();
        if (taskManager.size() == 0) return;

        System.out.print("Enter ID of task to update: ");
        int id = readInt();

        Optional<Task> opt = taskManager.getTaskById(id);
        if (opt.isEmpty()) {
            System.out.printf("[!] No task found with ID #%d.%n", id);
            return;
        }

        Task current = opt.get();
        System.out.println("\nLeave field blank to keep current value:");

        System.out.printf("Current Title [%s] -> New Title: ", current.getTitle());
        String newTitle = scanner.nextLine().trim();

        System.out.printf("Current Description [%s] -> New Description: ", current.getDescription());
        String newDesc = scanner.nextLine().trim();

        System.out.printf("Current Priority [%s] -> Update? (y/n): ", current.getPriority());
        Priority newPriority = null;
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            newPriority = selectPriority();
        }

        System.out.printf("Current Status [%s] -> Update? (y/n): ", current.getStatus());
        TaskStatus newStatus = null;
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            newStatus = selectStatus();
        }

        boolean updated = taskManager.updateTask(
                id,
                newTitle.isEmpty() ? null : newTitle,
                newDesc.isEmpty() ? null : newDesc,
                newPriority,
                newStatus
        );

        if (updated) {
            System.out.printf("%n[+] Task #%d updated and re-saved to JSON successfully!%n", id);
        } else {
            System.out.println("[!] Failed to update task.");
        }
    }

    private void handleDeleteTask() {
        System.out.println("\n--- DELETE TASK ---");
        handleListAllTasks();
        if (taskManager.size() == 0) return;

        System.out.print("Enter ID of task to delete: ");
        int id = readInt();

        Optional<Task> opt = taskManager.getTaskById(id);
        if (opt.isEmpty()) {
            System.out.printf("[!] No task found with ID #%d.%n", id);
            return;
        }

        System.out.printf("Are you sure you want to delete '%s' (ID #%d)? (y/n): ", opt.get().getTitle(), id);
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
            taskManager.deleteTask(id);
            System.out.printf("[+] Task #%d deleted and changes persisted to disk.%n", id);
        } else {
            System.out.println("[-] Deletion cancelled.");
        }
    }

    private void handleSearchTasks() {
        System.out.println("\n--- SEARCH TASKS ---");
        System.out.print("Enter search keyword (title/description): ");
        String query = scanner.nextLine().trim();
        List<Task> results = taskManager.searchTasks(query);
        displayTaskTable(results, "SEARCH RESULTS FOR: '" + query + "'");
    }

    private void handleFilterByStatus() {
        System.out.println("\n--- FILTER TASKS BY STATUS ---");
        TaskStatus status = selectStatus();
        List<Task> filtered = taskManager.filterByStatus(status);
        displayTaskTable(filtered, "TASKS WITH STATUS: " + status);
    }

    private void handleShowStorageInfo() {
        System.out.println("\n========================================================");
        System.out.println("             STORAGE DIAGNOSTICS & STATUS               ");
        System.out.println("========================================================");
        System.out.printf(" Storage Format      : JSON (Google Gson)%n");
        System.out.printf(" Storage File Path   : %s%n", taskManager.getStorage().getFilePath().toAbsolutePath());
        System.out.printf(" File Exists         : %s%n", java.nio.file.Files.exists(taskManager.getStorage().getFilePath()));
        System.out.printf(" Total Stored Tasks  : %d%n", taskManager.size());
        System.out.println(" Persistence Model   : Synchronous Atomic Write on Mutation");
        System.out.println(" Error Safeguards    : Corrupted JSON Auto-Backup (.bak)");
        System.out.println("========================================================");
    }

    private void displayTaskTable(List<Task> tasks, String title) {
        System.out.println("\n==========================================================================================");
        System.out.printf(" %s (Total: %d)%n", title, tasks.size());
        System.out.println("==========================================================================================");
        System.out.printf("| %-4s | %-24s | %-10s | %-12s | %-16s | %-12s |%n",
                "ID", "Title", "Priority", "Status", "Created At", "Description");
        System.out.println("+------+--------------------------+------------+--------------+------------------+--------------+");

        if (tasks.isEmpty()) {
            System.out.println("|                              No tasks found matching criteria.                          |");
        } else {
            for (Task t : tasks) {
                String safeTitle = truncate(t.getTitle(), 24);
                String safeDesc = truncate(t.getDescription(), 12);
                System.out.printf("| %-4d | %-24s | %-10s | %-12s | %-16s | %-12s |%n",
                        t.getId(), safeTitle, t.getPriority(), t.getStatus(), t.getCreatedAt(), safeDesc);
            }
        }
        System.out.println("==========================================================================================");
    }

    private String truncate(String val, int maxLen) {
        if (val == null) return "";
        return val.length() <= maxLen ? val : val.substring(0, maxLen - 3) + "...";
    }

    private Priority selectPriority() {
        System.out.println("Select Priority: 1. LOW | 2. MEDIUM | 3. HIGH (Default: 2)");
        System.out.print("Enter choice (1-3): ");
        if (!scanner.hasNextLine()) return Priority.MEDIUM;
        String pStr = scanner.nextLine().trim();
        return Priority.fromString(pStr);
    }

    private TaskStatus selectStatus() {
        System.out.println("Select Status: 1. PENDING | 2. IN_PROGRESS | 3. COMPLETED (Default: 1)");
        System.out.print("Enter choice (1-3): ");
        if (!scanner.hasNextLine()) return TaskStatus.PENDING;
        String sStr = scanner.nextLine().trim();
        return TaskStatus.fromString(sStr);
    }

    private String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) return "Sample Task";
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("[!] Field cannot be empty. Please enter text.");
        }
    }

    private int readIntChoice(int min, int max) {
        while (true) {
            System.out.printf("Select option (%d-%d): ", min, max);
            if (!scanner.hasNextLine()) return 0;
            String line = scanner.nextLine().trim();
            try {
                int c = Integer.parseInt(line);
                if (c >= min && c <= max) return c;
                System.out.printf("[!] Choice must be between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid menu number.");
            }
        }
    }

    private int readInt() {
        while (true) {
            if (!scanner.hasNextLine()) return 1;
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("[!] Invalid number. Please enter an integer ID: ");
            }
        }
    }
}
