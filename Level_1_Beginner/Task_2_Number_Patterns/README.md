# Level 1 - Task 2: Number Pattern Generator

## Overview
This module is developed as part of the **Cognifyz Technologies Software Development Internship**. It demonstrates algorithmic iteration, nested loop structuring, symmetry manipulation, and console formatting by dynamically generating six foundational mathematical number patterns based on user-specified row dimensions.

## Task Objectives
1. Implement a right-angled triangle pattern.
2. Implement an inverted right-angled triangle pattern.
3. Implement a centered palindromic number pyramid.
4. Implement Floyd's sequential number triangle.
5. Implement a repeated number triangle pattern.
6. Implement a symmetric number diamond pattern.
7. Support dynamic row input between 1 and 25 with full validation.
8. Support continuous interactive pattern generation until explicitly terminated.

## Features
- **Dynamic Row Sizing:** Accommodates any valid row input from 1 to 25 with automatic spacing adjustments.
- **Strict Input Validation:** Gracefully handles non-numeric strings, negative numbers, decimals, and empty entries.
- **Centering & Alignment:** Uses precise spacing calculations for pyramid and diamond symmetry.
- **Interactive Console Menu:** Easy-to-use menu with seamless repeat prompt.

## Java Concepts Used
- **Nested Loops:** `for` loops inside outer control loops to govern row/column coordinate iteration.
- **Formatting:** `System.out.printf()` for column-aligned display in Floyd's Triangle.
- **Input Stream Handling:** `java.util.Scanner` for resilient console interaction.
- **Exception Handling:** `try-catch` catching `NumberFormatException` to prevent application crashes.
- **Switch Expressions:** Clean Java 17 arrow switch syntax (`switch (choice) ->`).

## Source File Description
- `src/main/java/com/cognifyz/level1/task2/NumberPatternGenerator.java`: The core executable containing menu dispatching, user validation, and pattern generation algorithms.
- `pom.xml`: Maven configuration targeting Java 17.

## Prerequisites
- **JDK:** Java 17+
- **Build Tool:** Apache Maven 3.8+ (or standalone `javac`)

## Compilation & Execution

### Option A: Using Maven
From this directory (`Level_1_Beginner/Task_2_Number_Patterns`):
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
javac -d bin src/main/java/com/cognifyz/level1/task2/NumberPatternGenerator.java

# Run
java -cp bin com.cognifyz.level1.task2.NumberPatternGenerator
```

## Sample Output

### Pattern 3: Palindromic Number Pyramid (Rows = 4)
```
      1 
    1 2 1 
  1 2 3 2 1 
1 2 3 4 3 2 1 
```

### Pattern 4: Floyd's Triangle (Rows = 4)
```
1   
2   3   
4   5   6   
7   8   9   10  
```

### Pattern 6: Number Diamond (Rows = 4)
```
      1 
    1 2 1 
  1 2 3 2 1 
1 2 3 4 3 2 1 
  1 2 3 2 1 
    1 2 1 
      1 
```

## Testing Performed
1. **Dynamic Scaling:** Tested row counts from 1 (base boundary case), 5 (standard case), to 25 (upper limit case). Checked output consistency.
2. **Floyd's Triangle Alignment:** Confirmed numbers from 1 to 100+ align vertically in columns using format specifier `%-4d`.
3. **Invalid Input Handling:** Tested non-numeric inputs (`"triangle"`, `"-4"`, `"99"`); program warned user and re-prompted without failing.
4. **Interactive Repeat:** Verified loop continues upon `'y'` and exits cleanly upon `'n'` or menu choice `0`.
