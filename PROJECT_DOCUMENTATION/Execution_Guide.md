# Cognifyz Technologies Software Development Internship
## Execution and User Guide

### 1. Overview
This execution guide explains how to build, run, and test each of the six internship tasks independently. Every task contains its own self-contained source hierarchy, `pom.xml`, and standalone execution target.

---

### 2. Task-by-Task Execution Instructions

#### Level 1: Beginner

##### Task 1: Text-Based Game (Number Guessing Game)
- **Directory:** `Level_1_Beginner/Task_1_Text_Based_Game`
- **Main Class:** `com.cognifyz.level1.task1.TextBasedGame`
- **Run with Maven:**
  ```bash
  cd Level_1_Beginner/Task_1_Text_Based_Game
  mvn clean compile
  mvn exec:java
  ```
- **Run with Java CLI:**
  ```bash
  javac -d bin src/main/java/com/cognifyz/level1/task1/TextBasedGame.java
  java -cp bin com.cognifyz.level1.task1.TextBasedGame
  ```
- **How to Use:**
  1. The computer chooses a secret number between 1 and 100.
  2. Enter numbers to guess.
  3. Observe hints (`TOO HIGH` / `TOO LOW` / `Warm`).
  4. Solve within 15 attempts to win.
  5. Choose `y` to play another round or `n` to view the comprehensive session report.

---

##### Task 2: Number Patterns
- **Directory:** `Level_1_Beginner/Task_2_Number_Patterns`
- **Main Class:** `com.cognifyz.level1.task2.NumberPatternGenerator`
- **Run with Maven:**
  ```bash
  cd Level_1_Beginner/Task_2_Number_Patterns
  mvn clean compile
  mvn exec:java
  ```
- **Run with Java CLI:**
  ```bash
  javac -d bin src/main/java/com/cognifyz/level1/task2/NumberPatternGenerator.java
  java -cp bin com.cognifyz.level1.task2.NumberPatternGenerator
  ```
- **How to Use:**
  1. Select a pattern (1 to 6) from the interactive menu.
  2. Input the desired number of rows (1 to 25).
  3. View the generated pattern.
  4. Repeat with another pattern or choose option 0 to exit.

---

#### Level 2: Intermediate

##### Task 3: In-Memory CRUD Task Manager
- **Directory:** `Level_2_Intermediate/Task_3_CRUD_Application`
- **Main Class:** `com.cognifyz.level2.task3.TaskManagerApp`
- **Run with Maven:**
  ```bash
  cd Level_2_Intermediate/Task_3_CRUD_Application
  mvn clean compile
  mvn exec:java
  ```
- **Run with Java CLI:**
  ```bash
  javac -d bin src/main/java/com/cognifyz/level2/task3/*.java
  java -cp bin com.cognifyz.level2.task3.TaskManagerApp
  ```
- **How to Use:**
  - Option 1: View all registered tasks in a tabular format.
  - Option 2: Add a new task (enter title, description, priority, and status).
  - Option 3: Update existing tasks by specifying an ID.
  - Option 4: Delete a task by ID with confirmation prompt.
  - Option 5: Search tasks matching keywords in title or description.
  - Option 6: Filter tasks by status (`PENDING`, `IN_PROGRESS`, `COMPLETED`).

---

##### Task 4: Temperature Converter
- **Directory:** `Level_2_Intermediate/Task_4_Temperature_Converter`
- **Main Class:** `com.cognifyz.level2.task4.TemperatureConverter`
- **Run with Maven:**
  ```bash
  cd Level_2_Intermediate/Task_4_Temperature_Converter
  mvn clean compile
  mvn exec:java
  ```
- **Run with Java CLI:**
  ```bash
  javac -d bin src/main/java/com/cognifyz/level2/task4/TemperatureConverter.java
  java -cp bin com.cognifyz.level2.task4.TemperatureConverter
  ```
- **How to Use:**
  1. Choose a conversion direction (options 1 to 6).
  2. Input the numeric temperature value (supports decimals and negative values).
  3. View the converted result formatted to two decimal places along with the formula applied.
  4. Notice the built-in Absolute Zero physical check preventing unphysical inputs.
  5. Option 7 displays the formula reference guide.

---

#### Level 3: Advanced

##### Task 5: Persistent CRUD Task Manager (JSON & File I/O)
- **Directory:** `Level_3_Advanced/Task_5_CRUD_File_Persistence`
- **Main Class:** `com.cognifyz.level3.task5.Main`
- **Run with Maven:**
  ```bash
  cd Level_3_Advanced/Task_5_CRUD_File_Persistence
  mvn clean compile
  mvn exec:java
  ```
- **How to Use:**
  1. On launch, tasks are automatically deserialized from `data/tasks.json`.
  2. Perform Create, Read, Update, or Delete operations; all changes are immediately written to disk using atomic file replacements.
  3. Option 7 displays real-time disk storage diagnostics.
  4. Terminate and restart the program to confirm data persistence across sessions.

---

##### Task 6: Interactive Jsoup Web Scraper
- **Directory:** `Level_3_Advanced/Task_6_Web_Scraping`
- **Main Class:** `com.cognifyz.level3.task6.WebScraperApp`
- **Run with Maven:**
  ```bash
  cd Level_3_Advanced/Task_6_Web_Scraping
  mvn clean compile
  mvn exec:java
  ```
- **How to Use:**
  - Option 1: Quick scrape of top 10 books from `https://books.toscrape.com/`.
  - Option 2: Scrape custom target URL.
  - Option 3: Search books using keyword filters (e.g., `'Light'`, `'Art'`).
  - Option 4: Traverse pagination across multiple pages with configurable limits.
  - Option 5: Export all cached results to an RFC-4180 compliant CSV file (`scraped_books.csv`).

---

### 3. Verification Script
To compile all tasks in a single automated pass from PowerShell:
```powershell
$tasks = @(
    "Level_1_Beginner/Task_1_Text_Based_Game",
    "Level_1_Beginner/Task_2_Number_Patterns",
    "Level_2_Intermediate/Task_3_CRUD_Application",
    "Level_2_Intermediate/Task_4_Temperature_Converter",
    "Level_3_Advanced/Task_5_CRUD_File_Persistence",
    "Level_3_Advanced/Task_6_Web_Scraping"
)
foreach ($t in $tasks) {
    Write-Host ">>> Compiling $t" -ForegroundColor Cyan
    Push-Location $t
    mvn clean compile
    Pop-Location
}
```
