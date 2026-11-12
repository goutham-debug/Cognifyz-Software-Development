# Level 2 - Task 3: In-Memory CRUD Task Manager

## Overview
Developed as part of the **Cognifyz Technologies Software Development Internship**, this project implements a full-featured console-based task management system providing Create, Read, Update, and Delete (CRUD) operations, keyword-based search, and status filtering stored in memory using Java Collections.

## Task Objectives
1. Model tasks using an object-oriented `Task` class containing unique ID, title, description, priority, status, and creation timestamp.
2. Implement **Create** to register new tasks with incremental unique IDs.
3. Implement **Read** to list tasks in a clear, formatted console table.
4. Implement **Update** to modify task titles, descriptions, priorities, and statuses.
5. Implement **Delete** with confirmation safeguards.
6. Implement **Search** by title and description keywords.
7. Implement **Filter** by lifecycle status (`PENDING`, `IN_PROGRESS`, `COMPLETED`).
8. Ensure pure in-memory execution independent of Task 5.

## Features
- **Object-Oriented Architecture:** Clear separation of concerns among Domain Model (`Task`), Enumerations (`Priority`, `TaskStatus`), Business Logic (`TaskManager`), and Presentation CLI (`TaskManagerApp`).
- **Defensive Input Handling:** Rejects blank titles, invalid numerical IDs, and out-of-range choices.
- **Tabular Console Interface:** Clean ASCII grid formatting with automatic text truncation to maintain screen alignment.
- **Pre-seeded Demonstration Data:** Initializes with sample tasks for immediate testing of update, delete, and search workflows.

## Java Concepts Used
- **Collections Framework:** `java.util.ArrayList`, `java.util.Collections`, and Java Streams API (`filter`, `map`, `toList`).
- **Enumerations:** Type-safe enums for `Priority` (LOW, MEDIUM, HIGH) and `TaskStatus` (PENDING, IN_PROGRESS, COMPLETED).
- **Date & Time API:** `java.time.LocalDateTime` and `DateTimeFormatter` for human-readable audit timestamps.
- **Functional Programming:** Lambda expressions and stream operations for searching and filtering.
- **Encapsulation:** Immutable IDs, private attributes with accessor and mutator methods.

## Source File Descriptions
- `src/main/java/com/cognifyz/level2/task3/Task.java`: The core model representing a task item.
- `src/main/java/com/cognifyz/level2/task3/Priority.java`: Priority level enumeration with parsing helper.
- `src/main/java/com/cognifyz/level2/task3/TaskStatus.java`: Status lifecycle enumeration with parsing helper.
- `src/main/java/com/cognifyz/level2/task3/TaskManager.java`: Business service managing collection storage and CRUD operations.
- `src/main/java/com/cognifyz/level2/task3/TaskManagerApp.java`: Interactive command-line user interface and entry point.
- `pom.xml`: Maven project descriptor.

## Prerequisites
- **JDK:** Java 17+
- **Build Tool:** Apache Maven 3.8+ (or standalone `javac`)

## Compilation & Execution

### Option A: Using Maven
From this directory (`Level_2_Intermediate/Task_3_CRUD_Application`):
```bash
# Compile
mvn clean compile

# Run
mvn exec:java
```

### Option B: Using Standalone Java CLI
From this directory:
```bash
# Compile
javac -d bin src/main/java/com/cognifyz/level2/task3/*.java

# Run
java -cp bin com.cognifyz.level2.task3.TaskManagerApp
```

## Sample Output
```
========================================================
      COGNIFYZ TASK MANAGEMENT SYSTEM (IN-MEMORY)       
========================================================
 1. View All Tasks
 2. Add New Task
 3. Update Existing Task
 4. Delete Task
 5. Search Tasks by Keyword
 6. Filter Tasks by Status
 0. Exit Application
========================================================
Select option (0-6): 1

==========================================================================================
 ALL REGISTERED TASKS (Total: 3)
==========================================================================================
| ID   | Title                    | Priority   | Status       | Created At       | Description  |
+------+--------------------------+------------+--------------+------------------+--------------+
| 1    | Review Cognifyz Guide... | HIGH       | COMPLETED    | 2026-11-12 09:20 | Carefully... |
| 2    | Develop Task 3 CRUD      | HIGH       | IN_PROGRESS  | 2026-11-12 09:20 | Build in-... |
| 3    | Prepare Video Walkthr... | MEDIUM     | PENDING      | 2026-11-12 09:20 | Record Li... |
==========================================================================================
```

## Testing Performed
1. **Create Operation:** Added tasks with various priority and status combinations; verified unique auto-incrementing ID assignment (4, 5, etc.).
2. **Read Operation:** Listed tasks and verified all table columns format and align correctly.
3. **Update Operation:** Updated task title, priority, and status; confirmed modification persisted in memory.
4. **Delete Operation:** Tested task removal and confirmation prompt (`y`/`n`). Attempted deleting non-existent ID; verified error message.
5. **Search & Filter:** Searched keywords like `"Video"`; filtered by `COMPLETED` and `IN_PROGRESS` statuses; verified exact subset match.
