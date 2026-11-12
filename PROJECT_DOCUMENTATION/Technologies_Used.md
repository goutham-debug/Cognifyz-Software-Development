# Cognifyz Technologies Software Development Internship
## Technologies and Architectural Reference

### 1. Technology Stack Summary

| Technology | Version | Purpose | Tasks Utilizing |
|------------|---------|---------|-----------------|
| **Java SE** | 17 (LTS) | Core programming language | All Tasks (1 to 6) |
| **Apache Maven** | 3.9.6 | Project object model, build lifecycle, dependency resolution | All Tasks (1 to 6) |
| **Google Gson** | 2.11.0 | JSON serialization, deserialization, type reflection | Task 5 |
| **Jsoup** | 1.18.1 | HTML parser, HTTP networking, CSS DOM query selectors | Task 6 |
| **Java Collections** | Core | ArrayList, List, Collections, Streams API | Tasks 1, 3, 5, 6 |
| **Java Time API** | Core | LocalDateTime, DateTimeFormatter for audit logs | Tasks 3, 5 |
| **Java NIO File** | Core | Path, Files, StandardCopyOption for atomic disk persistence | Tasks 5, 6 |

---

### 2. Core Architectural Patterns Applied

#### 2.1 Object-Oriented Domain Modeling (OOP)
- **Encapsulation:** All model entities (`Task`, `BookItem`) use private fields with controlled public getters, setters, and factory behaviors.
- **Separation of Concerns:** Business logic (`TaskManager`, `WebScraper`) is cleanly separated from presentation logic (`TaskManagerApp`, `WebScraperApp`) and persistence logic (`TaskStorage`, `CsvExporter`).
- **Single Responsibility Principle (SRP):** Classes maintain a single purpose. For instance, `TaskStorage` is solely responsible for serializing and deserializing JSON, whereas `TaskManager` enforces CRUD business logic.

#### 2.2 Defensive Programming & Robust Error Handling
- **Input Sanitization:** Every console prompt validates user input using resilient `try-catch` blocks on numerical conversions.
- **Physical Validation:** In Task 4, thermal values are strictly bounded by absolute zero ($-273.15\text{ }^\circ\text{C}$, $-459.67\text{ }^\circ\text{F}$, $0\text{ K}$) before processing.
- **Self-Healing Persistence:** Task 5 catches `JsonSyntaxException` when encountering corrupted JSON data, automatically creates a timestamped backup (`.corrupt_*.bak`), and restarts cleanly.
- **Network Resilience:** Task 6 incorporates timeout limits (10s), custom User-Agent headers, URL format checking, and polite request throttling (300ms delay between pages).

#### 2.3 Standards Compliance
- **CSV RFC-4180:** All exported CSV data escaping quotes, commas, and newlines correctly, with UTF-8 BOM encoding for complete cross-platform compatibility with spreadsheet software (Excel, LibreOffice, Sheets).
- **Maven Standard Directory Layout:** All sub-projects strictly follow `src/main/java` standard structure for predictable compilation across CI/CD environments.
