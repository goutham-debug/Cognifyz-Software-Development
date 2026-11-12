# Level 2 - Task 4: Temperature Converter

## Overview
Developed as part of the **Cognifyz Technologies Software Development Internship**, this application provides high-precision, bidirectional temperature conversions between three principal thermodynamic scales: **Celsius**, **Fahrenheit**, and **Kelvin**.

## Task Objectives
1. Implement full bidirectional conversions across all three scales (6 distinct formulas):
   - Celsius to Fahrenheit / Fahrenheit to Celsius
   - Celsius to Kelvin / Kelvin to Celsius
   - Fahrenheit to Kelvin / Kelvin to Fahrenheit
2. Provide an interactive numeric menu allowing users to select conversion direction.
3. Support high-precision decimal inputs (integers, negative values, and floating-point numbers).
4. Format all converted results to two decimal places (`%.2f`).
5. Enforce **Absolute Zero** physical boundary validations to reject impossible thermodynamic states.
6. Handle invalid inputs gracefully with clear error recovery.

## Features
- **All 6 Scale Conversions:** Covers all standard thermal scale transitions.
- **Thermodynamic Law Enforcement:** Rejects values below absolute zero ($0\text{ K}$, $-273.15\text{ }^\circ\text{C}$, $-459.67\text{ }^\circ\text{F}$) with descriptive educational messages.
- **Formula Reference Guide:** Built-in menu option 7 displays explicit mathematical formulas and constants.
- **Repeated Conversion Loop:** Allows continuous batch testing without restarting the application.

## Java Concepts Used
- **Floating-Point Arithmetic:** `double` precision operations with careful order of operations.
- **Enhanced Switch Statements:** Java 17 switch expressions returning functional results.
- **Input Validation:** Number format parsing and range check loops.
- **String Formatting:** `System.out.printf()` with width and precision flags (`%.2f`).

## Source File Descriptions
- `src/main/java/com/cognifyz/level2/task4/TemperatureConverter.java`: Main class containing mathematical conversion routines, validation constraints, and user interface.
- `pom.xml`: Maven configuration targeting Java 17.

## Prerequisites
- **JDK:** Java 17+
- **Build Tool:** Apache Maven 3.8+ (or standalone `javac`)

## Compilation & Execution

### Option A: Using Maven
From this directory (`Level_2_Intermediate/Task_4_Temperature_Converter`):
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
javac -d bin src/main/java/com/cognifyz/level2/task4/TemperatureConverter.java

# Run
java -cp bin com.cognifyz.level2.task4.TemperatureConverter
```

## Sample Output
```
=======================================================
            COGNIFYZ TEMPERATURE CONVERTER            
=======================================================
 1. Celsius    (°C)  ->  Fahrenheit (°F)
 2. Fahrenheit (°F)  ->  Celsius    (°C)
 3. Celsius    (°C)  ->  Kelvin     (K)
 4. Kelvin     (K)   ->  Celsius    (°C)
 5. Fahrenheit (°F)  ->  Kelvin     (K)
 6. Kelvin     (K)   ->  Fahrenheit (°F)
 7. View Formula & Reference Guide
 0. Exit Application
=======================================================
Select option (0-7): 1
Enter temperature in °C (Celsius): 100

----------------- CONVERSION RESULT -----------------
 Input Temperature  : 100.00 °C (Celsius)
 Converted Value    : 212.00 °F (Fahrenheit)
 Formula Applied    : °F = (°C × 9/5) + 32
-----------------------------------------------------

Perform another temperature conversion? (y/n): y
```

## Physical Validation Test
```
Select option (0-7): 3
Enter temperature in °C (Celsius): -300
 [!] Physical Violation: -300.00 °C (Celsius) is below absolute zero (minimum: -273.15 °C (Celsius)).
     Temperatures below absolute zero are physically impossible. Please re-enter.
Enter temperature in °C (Celsius): 25

----------------- CONVERSION RESULT -----------------
 Input Temperature  : 25.00 °C (Celsius)
 Converted Value    : 298.15 K (Kelvin)
 Formula Applied    : K  = °C + 273.15
-----------------------------------------------------
```

## Testing Performed
1. **Benchmark Reference Points:**
   - Freezing point of water: $0\text{ }^\circ\text{C} = 32\text{ }^\circ\text{F} = 273.15\text{ K}$.
   - Boiling point of water: $100\text{ }^\circ\text{C} = 212\text{ }^\circ\text{F} = 373.15\text{ K}$.
   - Absolute zero: $-273.15\text{ }^\circ\text{C} = -459.67\text{ }^\circ\text{F} = 0.00\text{ K}$.
   All results verified accurate to within 2 decimal places.
2. **Boundary Violations:** Tested $-300\text{ }^\circ\text{C}$, $-500\text{ }^\circ\text{F}$, $-10\text{ K}$; rejected with physical violation alerts.
3. **Invalid Inputs:** Tested letters and empty inputs; handled gracefully without crash.
