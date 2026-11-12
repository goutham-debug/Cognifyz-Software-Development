# Level 3 - Task 5: CRUD Application with File Persistence

## Overview
Developed as part of the **Cognifyz Technologies Software Development Internship**, this application enhances the Task Management system with robust, automatic file persistence using JSON format and Google Gson. It ensures tasks survive application restarts, provides atomic disk writes, and incorporates automated recovery from corrupted data files.

## Task Objectives
1. Implement full Create, Read, Update, and Delete (CRUD) operations.
2. Automate file saving: persist task state immediately upon every creation, update, or deletion.
3. Automate file loading: reconstruct task collection automatically upon application launch.
4. Use Java File I/O and serialize/deserialize with **Google Gson**.
5. Maintain data integrity across application restarts.
6. Handle missing files gracefully by creating the file and parent directories if absent.
7. Implement defensive error recovery for malformed or corrupted JSON (creating a timestamped backup before recovery).
8. Maintain a strictly modular architecture across `Task`, `TaskManager`, `TaskStorage`, and `Main` classes.
9. Function completely independently of Task 3.

## Features
- **Zero-Data-Loss Architecture:** Immediate synchronous serialization on every mutating action.
- **Atomic File Writing:** Writes to a `.tmp` file and performs atomic replacement to guard against system crashes during writing.
- **Corrupted File Safe-Recovery:** Automatically detects `JsonSyntaxException`, creates a timestamped `.bak` backup copy of the damaged file, and recovers safely.
- **Storage Diagnostics:** Option 7 presents live diagnostic information including file size, canonical path, and item count.

## Java Concepts Used
- **JSON Serialization & Deserialization:** Google Gson (`GsonBuilder`, `TypeToken`).
- **NIO File System Operations:** `java.nio.file.Path`, `java.nio.file.Files`, `StandardCopyOption.ATOMIC_MOVE`.
- **Stream I/O:** `FileReader` and `FileWriter` wrapped inside try-with-resources.
- **Defensive Programming:** Multi-layered exception handling (`JsonSyntaxException`, `IOException`, `NumberFormatException`).
- **Separation of Concerns:** Strict decoupling between data model, storage engine, business rules, and presentation UI.

## Source File Descriptions
- `src/main/java/com/cognifyz/level3/task5/Task.java`: Model representing the task entity with default and parameterized constructors.
- `src/main/java/com/cognifyz/level3/task5/Priority.java`: Priority enumeration (LOW, MEDIUM, HIGH).
- `src/main/java/com/cognifyz/level3/task5/TaskStatus.java`: Status enumeration (PENDING, IN_PROGRESS, COMPLETED).
- `src/main/java/com/cognifyz/level3/task5/TaskStorage.java`: File I/O manager using Google Gson for disk persistence and corruption recovery.
- `src/main/java/com/cognifyz/level3/task5/TaskManager.java`: Business logic orchestrating task operations and calling storage.
- `src/main/java/com/cognifyz/level3/task5/Main.java`: Interactive console application entry point.
- `data/tasks.json`: Persistent JSON file storing task records.
- `pom.xml`: Maven configuration including the Google Gson 2.11.0 dependency.

## Prerequisites
- **JDK:** Java 17+
- **Build Tool:** Apache Maven 3.8+ (with internet access during initial build to fetch Gson)

## Compilation & Execution

### Option A: Using Maven (Recommended)
From this directory (`Level_3_Advanced/Task_5_CRUD_File_Persistence`):
```bash
# Compile and fetch dependencies
mvn clean compile

# Run
mvn exec:java
```

### Option B: Using Standalone Java CLI
From this directory:
```bash
# Compile with Gson jar in classpath
javac -cp "lib/*" -d bin src/main/java/com/cognifyz/level3/task5/*.java

# Run
java -cp "bin;lib/*" com.cognifyz.level3.task5.Main
```

## Sample Output
```
[INFO] Successfully loaded 3 task(s) from tasks.json

========================================================
   COGNIFYZ TASK MANAGER (JSON FILE PERSISTENCE)        
========================================================
 1. View All Tasks
 2. Add New Task (Auto-Saved)
 3. Update Existing Task (Auto-Saved)
 4. Delete Task (Auto-Saved)
 5. Search Tasks by Keyword
 6. Filter Tasks by Status
 7. View Persistent Storage Diagnostics
 0. Exit Application
========================================================
Select option (0-7): 1

==========================================================================================
 ALL PERSISTED TASKS (Total: 3)
==========================================================================================
| ID   | Title                    | Priority   | Status       | Created At       | Description  |
+------+--------------------------+------------+--------------+------------------+--------------+
| 1    | Configure Maven POM      | HIGH       | COMPLETED    | 2026-11-12 09:20 | Add Googl... |
| 2    | Implement TaskStorage    | HIGH       | COMPLETED    | 2026-11-12 09:20 | Implement... |
| 3    | Verify Session Persis... | MEDIUM     | IN_PROGRESS  | 2026-11-12 09:20 | Restart a... |
==========================================================================================
```

## Persistence Verification Test
1. **Creation & Save:** Added task #4 `"Build Task 6 Web Scraper"`. Exited application via option `0`.
2. **File Inspection:** Checked `data/tasks.json` on disk; verified task #4 was written in clean formatted JSON.
3. **Application Restart:** Re-launched the program; verified task #4 loaded immediately with correct ID, attributes, and timestamp.
4. **Resilience Testing:** Simulating a missing file by renaming `tasks.json`; application initialized cleanly without crashing and created a new file upon first save.
