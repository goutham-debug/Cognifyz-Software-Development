package com.cognifyz.level2.task3;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Cognifyz Technologies - Software Development Internship
 * Level 2: Intermediate - Task 3: In-Memory CRUD Task Manager
 *
 * Provides a user-friendly interactive console interface for managing
 * tasks stored in memory using Java Collections.
 *
 * @author Cognifyz Intern
 */
public class TaskManagerApp {

    private final TaskManager taskManager;
    private final Scanner scanner;

    public TaskManagerApp() {
        this.taskManager = new TaskManager();
        this.scanner = new Scanner(System.in);
        seedInitialData();
    }

    public static void main(String[] args) {
        TaskManagerApp app = new TaskManagerApp();
        app.run();
    }

    /**
     * Seeds initial sample tasks so the user can immediately test Read/Update/Delete.
     */
    private void seedInitialData() {
        taskManager.createTask("Review Cognifyz Guidelines", "Carefully read all PDF task instructions and rules.", Priority.HIGH, TaskStatus.COMPLETED);
        taskManager.createTask("Develop Task 3 CRUD", "Build in-memory task manager with clean OOP architecture.", Priority.HIGH, TaskStatus.IN_PROGRESS);
        taskManager.createTask("Prepare Video Walkthrough", "Record LinkedIn demonstration video of completed modules.", Priority.MEDIUM, TaskStatus.PENDING);
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntChoice(0, 6);
            switch (choice) {
                case 1 -> handleListAllTasks();
                case 2 -> handleCreateTask();
                case 3 -> handleUpdateTask();
                case 4 -> handleDeleteTask();
                case 5 -> handleSearchTasks();
                case 6 -> handleFilterByStatus();
                case 0 -> {
                    running = false;
                    System.out.println("\nExiting Cognifyz Task Manager. Goodbye!");
                }
            }
        }
    }

    private void printMenu() {
        System.out.println("\n========================================================");
        System.out.println("      COGNIFYZ TASK MANAGEMENT SYSTEM (IN-MEMORY)       ");
        System.out.println("========================================================");
        System.out.println(" 1. View All Tasks");
        System.out.println(" 2. Add New Task");
        System.out.println(" 3. Update Existing Task");
        System.out.println(" 4. Delete Task");
        System.out.println(" 5. Search Tasks by Keyword");
        System.out.println(" 6. Filter Tasks by Status");
        System.out.println(" 0. Exit Application");
        System.out.println("========================================================");
    }

    private void handleListAllTasks() {
        List<Task> tasks = taskManager.getAllTasks();
        displayTaskTable(tasks, "ALL REGISTERED TASKS");
    }

    private void handleCreateTask() {
        System.out.println("\n--- ADD NEW TASK ---");
        String title = readNonEmptyString("Enter task title: ");
        System.out.print("Enter task description (optional): ");
        String desc = scanner.nextLine().trim();

        Priority priority = selectPriority();
        TaskStatus status = selectStatus();

        Task created = taskManager.createTask(title, desc, priority, status);
        System.out.printf("%n[+] Task created successfully with ID #%d!%n", created.getId());
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
            System.out.printf("%n[+] Task #%d updated successfully!%n", id);
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
            System.out.printf("[+] Task #%d deleted successfully.%n", id);
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
