# Cognifyz Technologies Software Development Internship
## Installation and Environment Setup Guide

### 1. Overview
This installation guide provides complete instructions to set up the runtime environment, development tools, and dependencies required to build and execute all six tasks in the **Cognifyz Software Development Internship** submission.

---

### 2. System Requirements
- **Operating System:** Windows 10/11, Linux (Ubuntu 20.04+), or macOS (12.0+)
- **Architecture:** x64 / ARM64
- **RAM:** Minimum 4 GB (8 GB recommended)
- **Disk Space:** ~500 MB free space (for JDK, Maven, dependencies, and project source files)
- **Internet Connection:** Required for initial Maven dependency resolution (Google Gson for Task 5, Jsoup for Task 6).

---

### 3. Java Development Kit (JDK 17+)

The submission is authored in standard Java 17 and tested against OpenJDK 17 (Eclipse Temurin 17.0.21 LTS).

#### 3.1 Windows Installation
1. Download OpenJDK 17 MSI installer or zip from Adoptium:
   [https://adoptium.net/temurin/releases/?version=17](https://adoptium.net/temurin/releases/?version=17)
2. Or install via Windows Package Manager:
   ```powershell
   winget install EclipseAdoptium.Temurin.17.JDK
   ```
3. Set Environment Variables:
   - Variable Name: `JAVA_HOME`
   - Variable Value: `C:\Program Files\Eclipse Adoptium\jdk-17.0.21.9-hotspot` (or your JDK installation path)
   - Add `%JAVA_HOME%\bin` to your system `PATH`.
4. Verify Installation:
   ```cmd
   java -version
   javac -version
   ```

#### 3.2 Linux / macOS Installation
- **Ubuntu/Debian:**
  ```bash
  sudo apt update
  sudo apt install openjdk-17-jdk
  ```
- **macOS (Homebrew):**
  ```bash
  brew install openjdk@17
  ```

---

### 4. Apache Maven (3.8+)

Apache Maven is the build and dependency management tool used across all 6 tasks.

#### 4.1 Windows Installation
1. Download the Apache Maven binary zip (`apache-maven-3.9.x-bin.zip`):
   [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
2. Extract to a stable directory, e.g.:
   `C:\Program Files\apache-maven-3.9.6`
3. Add `C:\Program Files\apache-maven-3.9.6\bin` to your system `PATH`.
4. Verify Maven:
   ```cmd
   mvn -version
   ```

---

### 5. Third-Party Dependencies

Each task includes an isolated `pom.xml`. External libraries are automatically resolved by Maven upon build:

| Task | Library | Version | Group ID / Artifact ID | Purpose |
|------|---------|---------|-------------------------|---------|
| Task 1 | None (Core Java) | 17 | `java.base` | Random generation, Scanner I/O |
| Task 2 | None (Core Java) | 17 | `java.base` | Dynamic loops and nested algorithms |
| Task 3 | None (Core Java) | 17 | `java.base` | In-memory Collections, Streams |
| Task 4 | None (Core Java) | 17 | `java.base` | Floating-point thermal mathematics |
| Task 5 | **Google Gson** | `2.11.0` | `com.google.code.gson:gson` | JSON serialization, deserialization, file persistence |
| Task 6 | **Jsoup** | `1.18.1` | `org.jsoup:jsoup` | HTML parsing, HTTP client, CSS queries |

---

### 6. Workspace Verification
To verify your environment is ready to build all tasks:
```bash
# In any task directory:
mvn clean compile
```
If you see `[INFO] BUILD SUCCESS`, your environment is fully configured.
