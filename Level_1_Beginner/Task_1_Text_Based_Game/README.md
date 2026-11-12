# Level 1 - Task 1: Text-Based Game (Number Guessing Game)

## Overview
This project is part of the **Cognifyz Technologies Software Development Internship**. It implements an interactive console-based Number Guessing Game built in Java 17 adhering to clean code and object-oriented principles.

## Task Objectives
1. Generate a pseudo-random integer in the range `[1, 100]`.
2. Accept and validate user numeric guesses through the console.
3. Provide intuitive feedback (`TOO HIGH`, `TOO LOW`, with proximity hints when within 5 units).
4. Track and display the number of attempts made.
5. Display a winning celebration message with performance ratings upon victory.
6. Provide an option to play multiple rounds and retain session statistics.
7. Gracefully handle invalid inputs (non-numeric, negative, out-of-range, and blank values).

## Features
- **Adaptive Feedback:** Indicates directional hints (`TOO HIGH` / `TOO LOW`) and proximity alerts (`Getting warm!`).
- **Defensive Input Validation:** Rejects strings, symbols, empty lines, and out-of-range integers without crashing.
- **Round Statistics:** Tracks total rounds played, rounds won, win rate percentage, fewest attempts (best score), and average attempts per round.
- **Replay Capability:** Continuous interactive loop allowing the user to replay as many times as desired.

## Java Concepts Used
- **Control Flow:** `while` loops, `for` loops, multi-branch `if-else` conditionals.
- **Random Number Generation:** `java.util.Random` for pseudo-random number generation.
- **I/O & Streams:** `java.util.Scanner` for reading console input.
- **Exception Handling:** `try-catch` blocks handling `NumberFormatException`.
- **Encapsulation & OOP:** Structured class design with private state and helper methods.
- **String Formatting:** `System.out.printf()` for tabular and aligned console output.

## Source File Description
- `src/main/java/com/cognifyz/level1/task1/TextBasedGame.java`: The primary class containing game initialization, round loops, input sanitization, victory calculation, and statistical aggregation.
- `pom.xml`: Maven build configuration managing compiler settings (Java 17 target) and packaging.

## Prerequisites
- **JDK:** Java SE Development Kit 17 or higher
- **Build Tool:** Apache Maven 3.8+ (optional, can also run directly with `javac`/`java`)

## Compilation & Execution

### Option A: Using Maven (Recommended)
From this directory (`Level_1_Beginner/Task_1_Text_Based_Game`):
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
javac -d bin src/main/java/com/cognifyz/level1/task1/TextBasedGame.java

# Run
java -cp bin com.cognifyz.level1.task1.TextBasedGame
```

## Sample Output
```
=================================================
       COGNIFYZ TECHNOLOGIES INTERNSHIP          
        Level 1 - Task 1: Text-Based Game        
               NUMBER GUESSING GAME              
=================================================
 Rules:
 1. The computer picks a secret number from 1 to 100.
 2. You receive 'TOO HIGH' or 'TOO LOW' hints.
 3. Solve within 15 attempts to win the round.
 4. Have fun and test your binary search skills!
=================================================

========================================
 ROUND #1: Guess a number between 1 and 100
 You have a maximum of 15 attempts.
========================================
Attempt 1 - Enter your guess (1-100): 50
-> TOO LOW! Try a higher number.
   [Attempts used: 1 | Remaining: 14]
Attempt 2 - Enter your guess (1-100): 75
-> TOO HIGH! Try a lower number.
   [Attempts used: 2 | Remaining: 13]
Attempt 3 - Enter your guess (1-100): 62
-> TOO LOW, but you are very close! (Warm)
   [Attempts used: 3 | Remaining: 12]
Attempt 4 - Enter your guess (1-100): 65
****************************************
       CONGRATULATIONS! YOU WON!       
****************************************
 You guessed the correct number 65 in 4 attempt(s)!
 Rating: [Mastermind! Outstanding deductive logic]
****************************************

Do you want to play another round? (y/n): n

========================================
          GAME SESSION SUMMARY          
========================================
 Total Rounds Played : 1
 Rounds Won          : 1
 Rounds Lost         : 0
 Win Percentage      : 100.0%
 Best Score (Fewest) : 4 attempts
 Average Attempts    : 4.00
========================================
```

## Testing Performed
1. **Normal Guessing Path:** Tested binary search guesses (50, 75, etc.) until winning. Verified that hints, attempt counters, and victory rating display properly.
2. **Boundary Testing:** Tested boundary values `1` and `100`. Verified they are accepted without error.
3. **Invalid Input Handling:**
   - Entered negative numbers (e.g., `-5`) and numbers beyond range (e.g., `105`); received clear out-of-range warnings.
   - Entered non-numeric text (`"abc"`, `"$$$"`, empty enters); properly caught by exception handling without program termination.
4. **Replay Flow:** Confirmed user can reply `y` to play consecutive rounds, and cumulative session statistics update accurately upon termination with `n`.
