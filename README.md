# Cognifyz Technologies - Software Development Internship Submission

![Java 17](https://img.shields.io/badge/Java-17%20LTS-blue.svg)
![Build Tool](https://img.shields.io/badge/Build-Maven%203.9-orange.svg)
![Organization](https://img.shields.io/badge/Organization-Cognifyz%20Technologies-green.svg)
![Status](https://img.shields.io/badge/Status-Completed%20%26%20Verified-brightgreen.svg)

## Executive Summary
This repository contains the comprehensive, independently developed submission for the **Cognifyz Technologies Software Development Internship Program**. All **six (6)** prescribed tasks across Level 1 (Beginner), Level 2 (Intermediate), and Level 3 (Advanced) have been developed, compiled, rigorously tested, and packaged into this single submission bundle.

Each task is isolated within its own dedicated directory, featuring its own Java source code, Maven build configuration (`pom.xml`), and dedicated documentation (`README.md`).

---

## Repository Directory Structure

```text
Cognifyz_Internship_Submission/
│
├── README.md                                   # Root Submission Overview (this file)
│
├── Level_1_Beginner/
│   ├── Task_1_Text_Based_Game/                 # Task 1: Number Guessing Game
│   │   ├── pom.xml
│   │   ├── README.md
│   │   └── src/main/java/com/cognifyz/level1/task1/TextBasedGame.java
│   │
│   └── Task_2_Number_Patterns/                 # Task 2: Number Pattern Generator
│       ├── pom.xml
│       ├── README.md
│       └── src/main/java/com/cognifyz/level1/task2/NumberPatternGenerator.java
│
├── Level_2_Intermediate/
│   ├── Task_3_CRUD_Application/                # Task 3: In-Memory Task Manager
│   │   ├── pom.xml
│   │   ├── README.md
│   │   └── src/main/java/com/cognifyz/level2/task3/
│   │       ├── Priority.java
│   │       ├── TaskStatus.java
│   │       ├── Task.java
│   │       ├── TaskManager.java
│   │       └── TaskManagerApp.java
│   │
│   └── Task_4_Temperature_Converter/           # Task 4: Thermodynamic Converter
│       ├── pom.xml
│       ├── README.md
│       └── src/main/java/com/cognifyz/level2/task4/TemperatureConverter.java
│
├── Level_3_Advanced/
│   ├── Task_5_CRUD_File_Persistence/           # Task 5: Persistent JSON Task Manager
│   │   ├── pom.xml
│   │   ├── README.md
│   │   ├── data/
│   │   │   └── tasks.json
│   │   └── src/main/java/com/cognifyz/level3/task5/
│   │       ├── Priority.java
│   │       ├── TaskStatus.java
│   │       ├── Task.java
│   │       ├── TaskStorage.java
│   │       ├── TaskManager.java
│   │       └── Main.java
│   │
│   └── Task_6_Web_Scraping/                    # Task 6: Jsoup Web Scraper & CSV Exporter
│       ├── pom.xml
│       ├── README.md
│       └── src/main/java/com/cognifyz/level3/task6/
│           ├── BookItem.java
│           ├── WebScraper.java
│           ├── CsvExporter.java
│           └── WebScraperApp.java
│
└── PROJECT_DOCUMENTATION/
    ├── Installation_Guide.md                   # Environment and dependency setup
    ├── Execution_Guide.md                      # Compilation & execution instructions
    └── Technologies_Used.md                    # Architecture and technical references
```

---

## Tasks Overview

### Level 1: Beginner
* **Task 1: Text-Based Game (`TextBasedGame.java`)**
  * Console number guessing game with pseudo-random range `[1, 100]`.
  * Contextual hints (`TOO HIGH`, `TOO LOW`, and proximity `Warm` hints).
  * Dynamic attempt counter, victory celebration, and comprehensive multi-round statistical summary.
* **Task 2: Number Patterns (`NumberPatternGenerator.java`)**
  * Algorithmic generation of 6 distinct number patterns:
    1. Right-Angled Triangle
    2. Inverted Right-Angled Triangle
    3. Palindromic Number Pyramid
    4. Floyd's Triangle (aligned sequential columns)
    5. Repeated Number Triangle
    6. Symmetric Number Diamond
  * Dynamic user row input (1-25) with continuous interactive generation.

### Level 2: Intermediate
* **Task 3: In-Memory CRUD Application (`TaskManagerApp.java`)**
  * Console task manager managing tasks with unique IDs, titles, descriptions, priorities, statuses, and timestamps.
  * Implements Create, Read (tabular formatting), Update, Delete (with confirmation), Keyword Search, and Status Filtering.
  * Completely in-memory utilizing Java Collections (`ArrayList`).
* **Task 4: Temperature Converter (`TemperatureConverter.java`)**
  * Bidirectional conversion across Celsius, Fahrenheit, and Kelvin (6 conversion paths).
  * High-precision decimal arithmetic and two-decimal formatting (`%.2f`).
  * Enforces physical Absolute Zero constraints ($-273.15\text{ }^\circ\text{C}$, $-459.67\text{ }^\circ\text{F}$, $0\text{ K}$).

### Level 3: Advanced
* **Task 5: CRUD with File Persistence (`Main.java`)**
  * Persistent Task Manager using Google Gson for JSON disk serialization.
  * Auto-saves on every mutating operation and auto-loads upon startup.
  * Atomic file writing with temporary files to prevent data corruption.
  * Self-healing corrupted file recovery mechanism with automatic `.bak` backup generation.
* **Task 6: Interactive Web Scraping (`WebScraperApp.java`)**
  * Live HTML parsing of `https://books.toscrape.com/` using **Jsoup**.
  * Extracts Book Title, Price, Rating, Stock Status, and Product URL.
  * Supports pagination traversal, keyword filtering, result caps, and RFC-4180 CSV export.

---

## Quick Start: Compilation & Execution

Each task can be executed directly using Maven from its own subdirectory:

```bash
# Example: Running Task 1
cd Level_1_Beginner/Task_1_Text_Based_Game
mvn clean compile
mvn exec:java

# Example: Running Task 3
cd Level_2_Intermediate/Task_3_CRUD_Application
mvn clean compile
mvn exec:java

# Example: Running Task 5
cd Level_3_Advanced/Task_5_CRUD_File_Persistence
mvn clean compile
mvn exec:java

# Example: Running Task 6
cd Level_3_Advanced/Task_6_Web_Scraping
mvn clean compile
mvn exec:java
```

For detailed guides, refer to [Installation Guide](file:///PROJECT_DOCUMENTATION/Installation_Guide.md) and [Execution Guide](file:///PROJECT_DOCUMENTATION/Execution_Guide.md).

---

## Verification & QA Status
- [x] All 6 tasks have dedicated folders and `pom.xml` files.
- [x] All 6 tasks compile with `[INFO] BUILD SUCCESS` on Java 17+.
- [x] Task 1: Guessing logic, attempt tracking, and replay flow verified.
- [x] Task 2: All 6 pattern algorithms verified with dynamic row sizing.
- [x] Task 3: In-memory CRUD, search, and filtering verified.
- [x] Task 4: All 6 temperature formulas and absolute-zero guards verified.
- [x] Task 5: Automatic JSON file load, save, and persistence across restarts verified.
- [x] Task 6: Real live HTTP extraction from `books.toscrape.com` and CSV export verified.
- [x] Final single ZIP archive generated and validated.
