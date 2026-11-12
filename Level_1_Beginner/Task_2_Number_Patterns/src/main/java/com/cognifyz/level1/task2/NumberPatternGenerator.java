package com.cognifyz.level1.task2;

import java.util.Scanner;

/**
 * Cognifyz Technologies - Software Development Internship
 * Level 1: Beginner - Task 2: Number Pattern Generator
 *
 * Demonstrates loops, nested loops, dynamic user input handling,
 * and console formatting by generating 6 distinct mathematical number patterns.
 *
 * @author Cognifyz Intern
 */
public class NumberPatternGenerator {

    private static final int MIN_ROWS = 1;
    private static final int MAX_ROWS = 25;
    private final Scanner scanner;

    public NumberPatternGenerator() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        NumberPatternGenerator app = new NumberPatternGenerator();
        app.start();
    }

    /**
     * Main application loop presenting interactive pattern selection.
     */
    public void start() {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readIntegerChoice(0, 6);

            if (choice == 0) {
                running = false;
                System.out.println("\nExiting Number Pattern Generator. Thank you!");
                break;
            }

            int rows = readRowCount();
            System.out.println("\n----------------- GENERATED PATTERN -----------------");
            switch (choice) {
                case 1 -> generateRightAngledTriangle(rows);
                case 2 -> generateInvertedTriangle(rows);
                case 3 -> generateNumberPyramid(rows);
                case 4 -> generateFloydsTriangle(rows);
                case 5 -> generateRepeatedNumberPattern(rows);
                case 6 -> generateDiamondPattern(rows);
                default -> System.out.println("Unknown option selected.");
            }
            System.out.println("-----------------------------------------------------");

            running = askToContinue();
        }
    }

    private void printMenu() {
        System.out.println("\n=================================================");
        System.out.println("        COGNIFYZ NUMBER PATTERN GENERATOR        ");
        System.out.println("=================================================");
        System.out.println(" 1. Right-Angled Triangle Pattern");
        System.out.println(" 2. Inverted Right-Angled Triangle Pattern");
        System.out.println(" 3. Palindromic Number Pyramid Pattern");
        System.out.println(" 4. Floyd's Sequential Triangle Pattern");
        System.out.println(" 5. Repeated Number Triangle Pattern");
        System.out.println(" 6. Number Diamond Pattern");
        System.out.println(" 0. Exit Application");
        System.out.println("=================================================");
    }

    /**
     * Pattern 1: Right-Angled Triangle
     * 1
     * 1 2
     * 1 2 3
     * 1 2 3 4
     */
    public void generateRightAngledTriangle(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    /**
     * Pattern 2: Inverted Right-Angled Triangle
     * 1 2 3 4
     * 1 2 3
     * 1 2
     * 1
     */
    public void generateInvertedTriangle(int n) {
        for (int i = n; i >= 1; i--) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    /**
     * Pattern 3: Palindromic Centered Number Pyramid
     *       1
     *     1 2 1
     *   1 2 3 2 1
     * 1 2 3 4 3 2 1
     */
    public void generateNumberPyramid(int n) {
        for (int i = 1; i <= n; i++) {
            // Leading spaces for pyramid centering
            for (int s = 1; s <= (n - i) * 2; s++) {
                System.out.print(" ");
            }
            // Ascending sequence
            for (int j = 1; j <= i; j++) {
                System.out.print(j + " ");
            }
            // Descending sequence
            for (int j = i - 1; j >= 1; j--) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    /**
     * Pattern 4: Floyd's Triangle (continuous positive integers)
     * 1
     * 2 3
     * 4 5 6
     * 7 8 9 10
     */
    public void generateFloydsTriangle(int n) {
        int current = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.printf("%-4d", current++);
            }
            System.out.println();
        }
    }

    /**
     * Pattern 5: Repeated Number Pattern
     * 1
     * 2 2
     * 3 3 3
     * 4 4 4 4
     */
    public void generateRepeatedNumberPattern(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    /**
     * Pattern 6: Number Diamond Pattern
     *       1
     *     1 2 1
     *   1 2 3 2 1
     *     1 2 1
     *       1
     */
    public void generateDiamondPattern(int n) {
        // Upper half (including center row)
        for (int i = 1; i <= n; i++) {
            for (int s = 1; s <= (n - i) * 2; s++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print(j + " ");
            }
            for (int j = i - 1; j >= 1; j--) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        // Lower half
        for (int i = n - 1; i >= 1; i--) {
            for (int s = 1; s <= (n - i) * 2; s++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print(j + " ");
            }
            for (int j = i - 1; j >= 1; j--) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    private int readRowCount() {
        while (true) {
            System.out.printf("Enter number of rows (%d-%d): ", MIN_ROWS, MAX_ROWS);
            if (!scanner.hasNextLine()) return 4;
            String input = scanner.nextLine().trim();
            try {
                int rows = Integer.parseInt(input);
                if (rows < MIN_ROWS || rows > MAX_ROWS) {
                    System.out.printf(" [!] Please enter a value between %d and %d.%n", MIN_ROWS, MAX_ROWS);
                    continue;
                }
                return rows;
            } catch (NumberFormatException e) {
                System.out.println(" [!] Invalid input! Please enter a valid integer.");
            }
        }
    }

    private int readIntegerChoice(int min, int max) {
        while (true) {
            System.out.printf("Select an option (%d-%d): ", min, max);
            if (!scanner.hasNextLine()) return 0;
            String input = scanner.nextLine().trim();
            try {
                int val = Integer.parseInt(input);
                if (val < min || val > max) {
                    System.out.printf(" [!] Invalid option. Please choose between %d and %d.%n", min, max);
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println(" [!] Invalid input! Please enter a valid menu number.");
            }
        }
    }

    private boolean askToContinue() {
        while (true) {
            System.out.print("\nGenerate another pattern? (y/n): ");
            if (!scanner.hasNextLine()) return false;
            String ans = scanner.nextLine().trim().toLowerCase();
            if (ans.equals("y") || ans.equals("yes")) {
                return true;
            } else if (ans.equals("n") || ans.equals("no")) {
                System.out.println("Thank you for using the Number Pattern Generator!");
                return false;
            } else {
                System.out.println(" [!] Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
}
