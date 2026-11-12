package com.cognifyz.level1.task1;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Cognifyz Technologies - Software Development Internship
 * Level 1: Beginner - Task 1: Text-Based Game
 *
 * An interactive number guessing game that demonstrates conditional statements,
 * loops, input validation, and game state management in Java 17.
 *
 * @author Cognifyz Intern
 */
public class TextBasedGame {

    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 100;
    private static final int MAX_ATTEMPTS_LIMIT = 15;

    private final Random random;
    private final Scanner scanner;

    // Statistics tracking across rounds
    private int totalGames;
    private int gamesWon;
    private int bestScore;
    private int totalGuesses;

    public TextBasedGame() {
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.totalGames = 0;
        this.gamesWon = 0;
        this.bestScore = Integer.MAX_VALUE;
        this.totalGuesses = 0;
    }

    public static void main(String[] args) {
        TextBasedGame game = new TextBasedGame();
        game.start();
    }

    /**
     * Main game loop controlling rounds and replay option.
     */
    public void start() {
        printHeader();
        boolean keepPlaying = true;

        while (keepPlaying) {
            playRound();
            keepPlaying = askToPlayAgain();
        }

        printStatistics();
        System.out.println("\nThank you for playing the Cognifyz Number Guessing Game. Goodbye!");
    }

    /**
     * Executes a single game round.
     */
    private void playRound() {
        totalGames++;
        int targetNumber = random.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
        int attempts = 0;
        boolean hasWon = false;

        System.out.println("\n========================================");
        System.out.printf(" ROUND #%d: Guess a number between %d and %d%n", totalGames, MIN_RANGE, MAX_RANGE);
        System.out.printf(" You have a maximum of %d attempts.%n", MAX_ATTEMPTS_LIMIT);
        System.out.println("========================================");

        while (attempts < MAX_ATTEMPTS_LIMIT) {
            int guess = readValidGuess(attempts + 1);
            if (guess == -1) {
                return;
            }
            attempts++;
            totalGuesses++;

            if (guess == targetNumber) {
                hasWon = true;
                gamesWon++;
                if (attempts < bestScore) {
                    bestScore = attempts;
                }
                printVictory(targetNumber, attempts);
                break;
            } else if (guess < targetNumber) {
                int diff = targetNumber - guess;
                if (diff <= 5) {
                    System.out.println("-> TOO LOW, but you are very close! (Warm)");
                } else {
                    System.out.println("-> TOO LOW! Try a higher number.");
                }
            } else {
                int diff = guess - targetNumber;
                if (diff <= 5) {
                    System.out.println("-> TOO HIGH, but you are very close! (Warm)");
                } else {
                    System.out.println("-> TOO HIGH! Try a lower number.");
                }
            }

            int remaining = MAX_ATTEMPTS_LIMIT - attempts;
            if (remaining > 0) {
                System.out.printf("   [Attempts used: %d | Remaining: %d]%n", attempts, remaining);
            }
        }

        if (!hasWon) {
            System.out.println("\n----------------------------------------");
            System.out.printf(" GAME OVER! You ran out of attempts.%n");
            System.out.printf(" The secret number was: %d%n", targetNumber);
            System.out.println(" Better luck next time!");
            System.out.println("----------------------------------------");
        }
    }

    /**
     * Prompts the user and safely parses an integer between MIN_RANGE and MAX_RANGE.
     */
    private int readValidGuess(int attemptNumber) {
        while (true) {
            System.out.printf("Attempt %d - Enter your guess (%d-%d): ", attemptNumber, MIN_RANGE, MAX_RANGE);
            if (!scanner.hasNextLine()) {
                System.out.println("\n[!] End of input stream reached.");
                return -1;
            }
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(" [!] Input cannot be empty. Please enter a valid number.");
                continue;
            }

            try {
                int guess = Integer.parseInt(input);
                if (guess < MIN_RANGE || guess > MAX_RANGE) {
                    System.out.printf(" [!] Out of range! Guess must be between %d and %d.%n", MIN_RANGE, MAX_RANGE);
                    continue;
                }
                return guess;
            } catch (NumberFormatException e) {
                System.out.println(" [!] Invalid input! Please enter a whole number without letters or symbols.");
            }
        }
    }

    /**
     * Asks the user whether to start a new game round.
     */
    private boolean askToPlayAgain() {
        while (true) {
            System.out.print("\nDo you want to play another round? (y/n): ");
            if (!scanner.hasNextLine()) {
                return false;
            }
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            } else if (answer.equals("n") || answer.equals("no")) {
                return false;
            } else {
                System.out.println(" [!] Please type 'y' for yes or 'n' for no.");
            }
        }
    }

    /**
     * Prints the victory celebration banner with attempt rating.
     */
    private void printVictory(int secret, int attempts) {
        System.out.println("\n****************************************");
        System.out.println("       CONGRATULATIONS! YOU WON!       ");
        System.out.println("****************************************");
        System.out.printf(" You guessed the correct number %d in %d attempt(s)!%n", secret, attempts);

        if (attempts == 1) {
            System.out.println(" Rating: [Legendary / Telepathic! 100% Luck or Genius]");
        } else if (attempts <= 4) {
            System.out.println(" Rating: [Mastermind! Outstanding deductive logic]");
        } else if (attempts <= 8) {
            System.out.println(" Rating: [Great Job! Very efficient guessing]");
        } else {
            System.out.println(" Rating: [Good Effort! You persevered and solved it]");
        }
        System.out.println("****************************************");
    }

    /**
     * Prints game statistics at the end of the session.
     */
    private void printStatistics() {
        System.out.println("\n========================================");
        System.out.println("          GAME SESSION SUMMARY          ");
        System.out.println("========================================");
        System.out.printf(" Total Rounds Played : %d%n", totalGames);
        System.out.printf(" Rounds Won          : %d%n", gamesWon);
        System.out.printf(" Rounds Lost         : %d%n", (totalGames - gamesWon));
        double winRate = totalGames > 0 ? ((double) gamesWon / totalGames) * 100.0 : 0.0;
        System.out.printf(" Win Percentage      : %.1f%%%n", winRate);
        if (bestScore != Integer.MAX_VALUE) {
            System.out.printf(" Best Score (Fewest) : %d attempts%n", bestScore);
        }
        if (gamesWon > 0) {
            double avgGuesses = (double) totalGuesses / totalGames;
            System.out.printf(" Average Attempts    : %.2f%n", avgGuesses);
        }
        System.out.println("========================================");
    }

    private void printHeader() {
        System.out.println("=================================================");
        System.out.println("       COGNIFYZ TECHNOLOGIES INTERNSHIP          ");
        System.out.println("        Level 1 - Task 1: Text-Based Game        ");
        System.out.println("               NUMBER GUESSING GAME              ");
        System.out.println("=================================================");
        System.out.println(" Rules:");
        System.out.printf(" 1. The computer picks a secret number from %d to %d.%n", MIN_RANGE, MAX_RANGE);
        System.out.println(" 2. You receive 'TOO HIGH' or 'TOO LOW' hints.");
        System.out.printf(" 3. Solve within %d attempts to win the round.%n", MAX_ATTEMPTS_LIMIT);
        System.out.println(" 4. Have fun and test your binary search skills!");
        System.out.println("=================================================");
    }
}
