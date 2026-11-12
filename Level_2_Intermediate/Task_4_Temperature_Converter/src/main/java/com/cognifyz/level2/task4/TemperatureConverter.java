package com.cognifyz.level2.task4;

import java.util.Scanner;

/**
 * Cognifyz Technologies - Software Development Internship
 * Level 2: Intermediate - Task 4: Temperature Converter
 *
 * Implements bidirectional conversions between Celsius, Fahrenheit, and Kelvin
 * with absolute-zero physical boundary validation, decimal formatting, and error handling.
 *
 * @author Cognifyz Intern
 */
public class TemperatureConverter {

    // Physical absolute zero constants
    public static final double ABSOLUTE_ZERO_CELSIUS = -273.15;
    public static final double ABSOLUTE_ZERO_FAHRENHEIT = -459.67;
    public static final double ABSOLUTE_ZERO_KELVIN = 0.0;

    private final Scanner scanner;

    public TemperatureConverter() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        TemperatureConverter app = new TemperatureConverter();
        app.start();
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readMenuChoice(0, 7);

            if (choice == 0) {
                running = false;
                System.out.println("\nExiting Temperature Converter. Thank you!");
                break;
            }

            if (choice == 7) {
                displayFormulaGuide();
                continue;
            }

            executeConversion(choice);
            running = askToContinue();
        }
    }

    private void printMenu() {
        System.out.println("\n=======================================================");
        System.out.println("            COGNIFYZ TEMPERATURE CONVERTER            ");
        System.out.println("=======================================================");
        System.out.println(" 1. Celsius    (°C)  ->  Fahrenheit (°F)");
        System.out.println(" 2. Fahrenheit (°F)  ->  Celsius    (°C)");
        System.out.println(" 3. Celsius    (°C)  ->  Kelvin     (K)");
        System.out.println(" 4. Kelvin     (K)   ->  Celsius    (°C)");
        System.out.println(" 5. Fahrenheit (°F)  ->  Kelvin     (K)");
        System.out.println(" 6. Kelvin     (K)   ->  Fahrenheit (°F)");
        System.out.println(" 7. View Formula & Reference Guide");
        System.out.println(" 0. Exit Application");
        System.out.println("=======================================================");
    }

    private void executeConversion(int choice) {
        String fromUnit = getFromUnit(choice);
        String toUnit = getToUnit(choice);
        double minAllowed = getMinAllowed(choice);

        double inputVal = readTemperatureInput(fromUnit, minAllowed);
        double resultVal = performCalculation(choice, inputVal);

        System.out.println("\n----------------- CONVERSION RESULT -----------------");
        System.out.printf(" Input Temperature  : %.2f %s%n", inputVal, fromUnit);
        System.out.printf(" Converted Value    : %.2f %s%n", resultVal, toUnit);
        System.out.println(" Formula Applied    : " + getFormulaString(choice));
        System.out.println("-----------------------------------------------------");
    }

    public double performCalculation(int choice, double temp) {
        return switch (choice) {
            case 1 -> celsiusToFahrenheit(temp);
            case 2 -> fahrenheitToCelsius(temp);
            case 3 -> celsiusToKelvin(temp);
            case 4 -> kelvinToCelsius(temp);
            case 5 -> fahrenheitToKelvin(temp);
            case 6 -> kelvinToFahrenheit(temp);
            default -> throw new IllegalArgumentException("Unknown conversion choice: " + choice);
        };
    }

    // Direct Conversion Methods
    public double celsiusToFahrenheit(double c) {
        return (c * 9.0 / 5.0) + 32.0;
    }

    public double fahrenheitToCelsius(double f) {
        return (f - 32.0) * 5.0 / 9.0;
    }

    public double celsiusToKelvin(double c) {
        return c + 273.15;
    }

    public double kelvinToCelsius(double k) {
        return k - 273.15;
    }

    public double fahrenheitToKelvin(double f) {
        return (f - 32.0) * 5.0 / 9.0 + 273.15;
    }

    public double kelvinToFahrenheit(double k) {
        return (k - 273.15) * 9.0 / 5.0 + 32.0;
    }

    private double readTemperatureInput(String unit, double minThreshold) {
        while (true) {
            System.out.printf("Enter temperature in %s: ", unit);
            if (!scanner.hasNextLine()) return 25.0;
            String line = scanner.nextLine().trim();
            try {
                double val = Double.parseDouble(line);
                if (val < minThreshold) {
                    System.out.printf(" [!] Physical Violation: %.2f %s is below absolute zero (minimum: %.2f %s).%n",
                            val, unit, minThreshold, unit);
                    System.out.println("     Temperatures below absolute zero are physically impossible. Please re-enter.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println(" [!] Invalid numerical input. Decimal numbers (e.g. 25.5, -10.0) are supported.");
            }
        }
    }

    private int readMenuChoice(int min, int max) {
        while (true) {
            System.out.printf("Select option (%d-%d): ", min, max);
            if (!scanner.hasNextLine()) return 0;
            String line = scanner.nextLine().trim();
            try {
                int c = Integer.parseInt(line);
                if (c >= min && c <= max) return c;
                System.out.printf(" [!] Choice must be between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println(" [!] Invalid input. Please enter a number.");
            }
        }
    }

    private boolean askToContinue() {
        while (true) {
            System.out.print("\nPerform another temperature conversion? (y/n): ");
            if (!scanner.hasNextLine()) return false;
            String ans = scanner.nextLine().trim().toLowerCase();
            if (ans.equals("y") || ans.equals("yes")) return true;
            if (ans.equals("n") || ans.equals("no")) {
                System.out.println("Thank you for using the Temperature Converter!");
                return false;
            }
            System.out.println(" [!] Please enter 'y' for yes or 'n' for no.");
        }
    }

    private String getFromUnit(int choice) {
        return switch (choice) {
            case 1, 3 -> "°C (Celsius)";
            case 2, 5 -> "°F (Fahrenheit)";
            case 4, 6 -> "K (Kelvin)";
            default -> "";
        };
    }

    private String getToUnit(int choice) {
        return switch (choice) {
            case 1, 6 -> "°F (Fahrenheit)";
            case 2, 4 -> "°C (Celsius)";
            case 3, 5 -> "K (Kelvin)";
            default -> "";
        };
    }

    private double getMinAllowed(int choice) {
        return switch (choice) {
            case 1, 3 -> ABSOLUTE_ZERO_CELSIUS;
            case 2, 5 -> ABSOLUTE_ZERO_FAHRENHEIT;
            case 4, 6 -> ABSOLUTE_ZERO_KELVIN;
            default -> -Double.MAX_VALUE;
        };
    }

    private String getFormulaString(int choice) {
        return switch (choice) {
            case 1 -> "°F = (°C × 9/5) + 32";
            case 2 -> "°C = (°F - 32) × 5/9";
            case 3 -> "K  = °C + 273.15";
            case 4 -> "°C = K - 273.15";
            case 5 -> "K  = (°F - 32) × 5/9 + 273.15";
            case 6 -> "°F = (K - 273.15) × 9/5 + 32";
            default -> "";
        };
    }

    private void displayFormulaGuide() {
        System.out.println("\n=======================================================");
        System.out.println("         TEMPERATURE CONVERSION FORMULA GUIDE          ");
        System.out.println("=======================================================");
        System.out.println(" 1. Celsius to Fahrenheit : °F = (°C × 9/5) + 32");
        System.out.println(" 2. Fahrenheit to Celsius : °C = (°F - 32) × 5/9");
        System.out.println(" 3. Celsius to Kelvin     : K  = °C + 273.15");
        System.out.println(" 4. Kelvin to Celsius     : °C = K - 273.15");
        System.out.println(" 5. Fahrenheit to Kelvin  : K  = (°F - 32) × 5/9 + 273.15");
        System.out.println(" 6. Kelvin to Fahrenheit  : °F = (K - 273.15) × 9/5 + 32");
        System.out.println("-------------------------------------------------------");
        System.out.println(" Physical Constraints (Absolute Zero):");
        System.out.println(" - Celsius    : >= -273.15 °C");
        System.out.println(" - Fahrenheit : >= -459.67 °F");
        System.out.println(" - Kelvin     : >= 0.00 K");
        System.out.println("=======================================================");
    }
}
