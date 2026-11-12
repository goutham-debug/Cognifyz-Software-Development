# Level 3 - Task 6: Interactive Web Scraping with Jsoup

## Overview
Developed as part of the **Cognifyz Technologies Software Development Internship**, this application implements an interactive web scraping suite using **Jsoup**. It extracts real-time product data from live HTML web documents, parses structural elements, navigates multi-page pagination, applies keyword filters, and exports extracted datasets into RFC-compliant CSV files.

## Task Objectives
1. Connect via HTTP/HTTPS to public target sites (defaulting to the scraping sandbox `https://books.toscrape.com/`).
2. Parse HTML DOM structures using Jsoup CSS selectors.
3. Extract core product data attributes:
   - Book Title (full title from tag attribute/text)
   - Price (formatted currency value)
   - Availability Status (stock status)
   - Rating (converted from CSS classes to descriptive star strings)
   - Product Page URL (absolute URL)
4. Implement dynamic URL selection with input validation.
5. Provide user-friendly tabular console output.
6. Support limit caps on total items extracted.
7. Support case-insensitive title keyword filtering.
8. Support multi-page traversal through pagination links (`li.next > a`).
9. Implement RFC-4180 compliant CSV export with automatic quote and delimiter escaping.
10. Defensively handle connection timeouts, HTTP error codes, invalid URLs, and network disconnects.
11. Execute real live HTTP requests and live parsing without simulated mock data.

## Features
- **Real-Time Extraction:** Executes authentic live HTTP GET requests to `books.toscrape.com`.
- **Intelligent CSS Parsing:** Extracts data using resilient selectors (`article.product_pod`, `h3 > a`, `p.price_color`, `p.star-rating`, `p.instock`).
- **Rating Normalization:** Converts CSS rating classes (`One`, `Two`, `Three`, `Four`, `Five`) into visual star strings (e.g. `★★★☆☆ (3/5)`).
- **Pagination Engine:** Automatically tracks the `next` page link across multiple pages while respecting server politeness delays (300ms throttle).
- **Keyword Filtering:** Filters extracted items on-the-fly based on user-supplied substrings.
- **CSV Exporter:** Exports parsed datasets to disk with UTF-8 BOM encoding for seamless viewing in Microsoft Excel or Google Sheets.

## Java Concepts Used
- **External Library Integration:** Jsoup (`org.jsoup.Jsoup`, `org.jsoup.nodes.Document`, `org.jsoup.select.Elements`).
- **Network I/O & HTTP:** User-Agent header spoofing, timeout configurations, referrer setting.
- **URL Syntax Validation:** `java.net.URL`, `MalformedURLException`.
- **Character Streams & File Writing:** `BufferedWriter`, `FileWriter`, RFC-4180 CSV escaping.
- **Collections & Streams:** Java Collections Framework for data aggregation and manipulation.

## Source File Descriptions
- `src/main/java/com/cognifyz/level3/task6/BookItem.java`: Domain model encapsulating book details and rating normalization.
- `src/main/java/com/cognifyz/level3/task6/WebScraper.java`: Core scraping engine handling HTTP requests, HTML parsing, and pagination traversal.
- `src/main/java/com/cognifyz/level3/task6/CsvExporter.java`: File export utility formatting datasets into valid CSV format.
- `src/main/java/com/cognifyz/level3/task6/WebScraperApp.java`: Interactive CLI entry point with multi-mode menus.
- `pom.xml`: Maven configuration containing Jsoup 1.18.1 dependency.

## Prerequisites
- **JDK:** Java 17+
- **Build Tool:** Apache Maven 3.8+
- **Network:** Active internet connection to fetch target web pages from `books.toscrape.com`.

## Compilation & Execution

### Option A: Using Maven (Recommended)
From this directory (`Level_3_Advanced/Task_6_Web_Scraping`):
```bash
# Compile and fetch dependencies
mvn clean compile

# Run
mvn exec:java
```

### Option B: Using Standalone Java CLI
From this directory:
```bash
# Compile with Jsoup jar in classpath
javac -cp "lib/*" -d bin src/main/java/com/cognifyz/level3/task6/*.java

# Run
java -cp "bin;lib/*" com.cognifyz.level3.task6.WebScraperApp
```

## Sample Output
```
==========================================================
         COGNIFYZ INTERACTIVE JSOUP WEB SCRAPER           
==========================================================
 Target: https://books.toscrape.com/
 Current cached items in memory: 0
----------------------------------------------------------
 1. Quick Scrape (First 10 Books from Default Site)
 2. Custom Target URL Scrape
 3. Scrape with Keyword Filter (e.g. 'Light', 'Art')
 4. Multi-Page Deep Scrape (Configurable Pages & Limits)
 5. Export Scraped Data to CSV File
 0. Exit Application
==========================================================
Select option (0-5): 1

--- QUICK SCRAPE (Top 10 Books) ---
[SCRAPER] Connecting to: https://books.toscrape.com/
[SCRAPER] Fetching Page #1 -> https://books.toscrape.com/
[SCRAPER] Desired limit of 10 items reached.

============================================================================================================
 QUICK SCRAPE RESULTS (Total Extracted: 10)
============================================================================================================
| #    | Book Title                           | Price      | Rating           | Availability |
+------+--------------------------------------+------------+------------------+--------------+
| 1    | A Light in the Attic                 | £51.77     | ★★★☆☆ (3/5)      | In stock     |
| 2    | Tipping the Velvet                   | £53.74     | ★☆☆☆☆ (1/5)      | In stock     |
| 3    | Soumission                           | £50.10     | ★☆☆☆☆ (1/5)      | In stock     |
| 4    | Sharp Objects                        | £47.82     | ★★★★☆ (4/5)      | In stock     |
| 5    | Sapiens: A Brief History of Humankind| £54.23     | ★★★★★ (5/5)      | In stock     |
| 6    | The Requiem Red                      | £22.65     | ★☆☆☆☆ (1/5)      | In stock     |
| 7    | The Dirty Little Secrets of Gettin...| £33.34     | ★★★★☆ (4/5)      | In stock     |
| 8    | The Coming Woman: A Novel Based on...| £17.93     | ★★★☆☆ (3/5)      | In stock     |
| 9    | The Boys in the Boat: Nine America...| £22.60     | ★★★★☆ (4/5)      | In stock     |
| 10   | The Black Maria                      | £52.15     | ★☆☆☆☆ (1/5)      | In stock     |
============================================================================================================

Would you like to export these results to a CSV file? (y/n): y

--- EXPORT DATA TO CSV ---
Enter export filename/path (Default: scraped_books.csv): 
[+] Successfully exported 10 records to CSV!
    Saved at: C:\Users\Goutham\.gemini\antigravity\scratch\Cognifyz_Internship_Submission\Level_3_Advanced\Task_6_Web_Scraping\scraped_books.csv
```

## Testing Performed
1. **Live Network Extraction:** Verified actual HTTP connection to `https://books.toscrape.com/` extracting real book titles, pound currency prices, and stock indicators.
2. **Pagination Logic:** Executed option 4 across 3 consecutive pages (`page-1.html`, `page-2.html`, `page-3.html`); successfully gathered 40+ unique books.
3. **Keyword Filtering:** Searched for `"Light"`; verified only matching titles such as `"A Light in the Attic"` were returned.
4. **CSV Export & Integrity:** Generated `scraped_books.csv`; confirmed proper header line, quotation marks for titles with commas, and accurate columns.
5. **Network Resilience & Error Trapping:** Tested invalid URLs (`"not_a_url"`, `"ftp://invalid"`); verified descriptive user error messages without application termination.
