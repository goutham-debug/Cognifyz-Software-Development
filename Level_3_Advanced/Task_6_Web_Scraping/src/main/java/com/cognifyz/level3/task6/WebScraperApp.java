package com.cognifyz.level3.task6;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Cognifyz Technologies - Software Development Internship
 * Level 3: Advanced - Task 6: Interactive Web Scraping
 *
 * Provides an interactive console interface for scraping real-time website
 * data using Jsoup, filtering items, traversing pagination, and exporting to CSV.
 *
 * @author Cognifyz Intern
 */
public class WebScraperApp {

    private final WebScraper scraper;
    private final Scanner scanner;
    private List<BookItem> lastResults;

    public WebScraperApp() {
        this.scraper = new WebScraper();
        this.scanner = new Scanner(System.in);
        this.lastResults = new ArrayList<>();
    }

    public static void main(String[] args) {
        WebScraperApp app = new WebScraperApp();
        app.run();
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntChoice(0, 5);
            switch (choice) {
                case 1 -> handleQuickScrape();
                case 2 -> handleCustomUrlScrape();
                case 3 -> handleKeywordSearch();
                case 4 -> handleDeepMultiPageScrape();
                case 5 -> handleCsvExport();
                case 0 -> {
                    running = false;
                    System.out.println("\nExiting Web Scraper. Thank you!");
                }
            }
        }
    }

    private void printMenu() {
        System.out.println("\n==========================================================");
        System.out.println("         COGNIFYZ INTERACTIVE JSOUP WEB SCRAPER           ");
        System.out.println("==========================================================");
        System.out.println(" Target: " + WebScraper.DEFAULT_URL);
        System.out.println(" Current cached items in memory: " + lastResults.size());
        System.out.println("----------------------------------------------------------");
        System.out.println(" 1. Quick Scrape (First 10 Books from Default Site)");
        System.out.println(" 2. Custom Target URL Scrape");
        System.out.println(" 3. Scrape with Keyword Filter (e.g. 'Light', 'Art')");
        System.out.println(" 4. Multi-Page Deep Scrape (Configurable Pages & Limits)");
        System.out.println(" 5. Export Scraped Data to CSV File");
        System.out.println(" 0. Exit Application");
        System.out.println("==========================================================");
    }

    private void handleQuickScrape() {
        System.out.println("\n--- QUICK SCRAPE (Top 10 Books) ---");
        try {
            lastResults = scraper.scrapeBooks(WebScraper.DEFAULT_URL, 10, null, 1);
            displayTable(lastResults, "QUICK SCRAPE RESULTS");
            promptCsvExport();
        } catch (IOException e) {
            System.err.println("[!] Connection or Parsing Error: " + e.getMessage());
        }
    }

    private void handleCustomUrlScrape() {
        System.out.println("\n--- CUSTOM TARGET URL SCRAPE ---");
        System.out.printf("Enter website URL (Press Enter for default '%s'): ", WebScraper.DEFAULT_URL);
        String urlInput = scanner.nextLine().trim();
        String targetUrl = urlInput.isEmpty() ? WebScraper.DEFAULT_URL : urlInput;

        try {
            WebScraper.validateUrl(targetUrl);
        } catch (MalformedURLException e) {
            System.out.println("[!] Invalid URL structure: " + e.getMessage());
            return;
        }

        System.out.print("Enter maximum items to fetch (e.g. 15): ");
        int limit = readPositiveInt(15);

        try {
            lastResults = scraper.scrapeBooks(targetUrl, limit, null, 2);
            displayTable(lastResults, "CUSTOM SCRAPE RESULTS FROM " + targetUrl);
            promptCsvExport();
        } catch (IOException e) {
            System.err.println("[!] Scraping Error: " + e.getMessage());
        }
    }

    private void handleKeywordSearch() {
        System.out.println("\n--- SCRAPE WITH KEYWORD FILTER ---");
        System.out.print("Enter book title keyword (e.g. 'Light', 'History', 'World'): ");
        String keyword = scanner.nextLine().trim();
        if (keyword.isEmpty()) {
            System.out.println("[!] Keyword cannot be empty.");
            return;
        }

        System.out.print("How many pages to search across? (1-5, Default: 3): ");
        int pages = readPositiveInt(3);

        try {
            lastResults = scraper.scrapeBooks(WebScraper.DEFAULT_URL, 20, keyword, pages);
            displayTable(lastResults, "KEYWORD FILTER RESULTS FOR: '" + keyword + "'");
            promptCsvExport();
        } catch (IOException e) {
            System.err.println("[!] Search Error: " + e.getMessage());
        }
    }

    private void handleDeepMultiPageScrape() {
        System.out.println("\n--- MULTI-PAGE DEEP SCRAPE ---");
        System.out.print("Enter number of pages to traverse (1-10, Default: 2): ");
        int pages = readPositiveInt(2);
        System.out.print("Enter max total items to collect (e.g. 40): ");
        int limit = readPositiveInt(40);

        try {
            lastResults = scraper.scrapeBooks(WebScraper.DEFAULT_URL, limit, null, pages);
            displayTable(lastResults, String.format("DEEP SCRAPE RESULTS (%d pages max)", pages));
            promptCsvExport();
        } catch (IOException e) {
            System.err.println("[!] Deep Scrape Error: " + e.getMessage());
        }
    }

    private void handleCsvExport() {
        if (lastResults.isEmpty()) {
            System.out.println("[!] No scraped data in memory. Please execute a scrape first.");
            return;
        }

        System.out.println("\n--- EXPORT DATA TO CSV ---");
        System.out.print("Enter export filename/path (Default: scraped_books.csv): ");
        String path = scanner.nextLine().trim();
        if (path.isEmpty()) {
            path = "scraped_books.csv";
        }
        if (!path.toLowerCase().endsWith(".csv")) {
            path += ".csv";
        }

        try {
            String savedPath = CsvExporter.exportToCsv(lastResults, path);
            System.out.printf("[+] Successfully exported %d records to CSV!%n", lastResults.size());
            System.out.printf("    Saved at: %s%n", savedPath);
        } catch (IOException e) {
            System.err.println("[!] Failed to write CSV file: " + e.getMessage());
        }
    }

    private void promptCsvExport() {
        if (lastResults.isEmpty()) return;
        System.out.print("\nWould you like to export these results to a CSV file? (y/n): ");
        if (!scanner.hasNextLine()) return;
        String ans = scanner.nextLine().trim().toLowerCase();
        if (ans.equals("y") || ans.equals("yes")) {
            handleCsvExport();
        }
    }

    private void displayTable(List<BookItem> books, String title) {
        System.out.println("\n============================================================================================================");
        System.out.printf(" %s (Total Extracted: %d)%n", title, books.size());
        System.out.println("============================================================================================================");
        System.out.printf("| %-4s | %-36s | %-10s | %-16s | %-12s |%n",
                "#", "Book Title", "Price", "Rating", "Availability");
        System.out.println("+------+--------------------------------------+------------+------------------+--------------+");

        if (books.isEmpty()) {
            System.out.println("|                              No books found matching search criteria.                                      |");
        } else {
            int i = 1;
            for (BookItem b : books) {
                String safeTitle = truncate(b.getTitle(), 36);
                System.out.printf("| %-4d | %-36s | %-10s | %-16s | %-12s |%n",
                        i++, safeTitle, b.getPrice(), b.getRating(), b.getAvailability());
            }
        }
        System.out.println("============================================================================================================");
    }

    private String truncate(String text, int max) {
        if (text == null) return "";
        return text.length() <= max ? text : text.substring(0, max - 3) + "...";
    }

    private int readIntChoice(int min, int max) {
        while (true) {
            System.out.printf("Select option (%d-%d): ", min, max);
            if (!scanner.hasNextLine()) return 0;
            String line = scanner.nextLine().trim();
            try {
                int c = Integer.parseInt(line);
                if (c >= min && c <= max) return c;
                System.out.printf("[!] Choice must be between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid choice. Please enter a valid number.");
            }
        }
    }

    private int readPositiveInt(int defaultVal) {
        if (!scanner.hasNextLine()) return defaultVal;
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) return defaultVal;
        try {
            int v = Integer.parseInt(line);
            return v > 0 ? v : defaultVal;
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }
}
