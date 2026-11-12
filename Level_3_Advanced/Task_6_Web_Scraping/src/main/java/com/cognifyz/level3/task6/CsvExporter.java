package com.cognifyz.level3.task6;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Exports scraped book data to RFC-4180 compliant CSV files.
 */
public class CsvExporter {

    /**
     * Writes list of BookItem objects to a CSV file.
     *
     * @param items List of books
     * @param targetFilePath Destination path
     * @return Absolute file path on success
     * @throws IOException on write error
     */
    public static String exportToCsv(List<BookItem> items, String targetFilePath) throws IOException {
        Path path = Paths.get(targetFilePath);
        File parent = path.toFile().getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), StandardCharsets.UTF_8))) {
            // Write CSV Header with BOM for Excel compatibility
            writer.write('\ufeff');
            writer.write("Index,Title,Price,Rating,Availability,Product URL");
            writer.newLine();

            int index = 1;
            for (BookItem b : items) {
                writer.write(String.format("%d,%s,%s,%s,%s,%s",
                        index++,
                        escapeCsv(b.getTitle()),
                        escapeCsv(b.getPrice()),
                        escapeCsv(b.getRating()),
                        escapeCsv(b.getAvailability()),
                        escapeCsv(b.getProductUrl())));
                writer.newLine();
            }
        }

        return path.toAbsolutePath().toString();
    }

    private static String escapeCsv(String field) {
        if (field == null) return "\"\"";
        // If field contains comma, quote, or newline, escape it
        if (field.contains(",") || field.contains("\"") || field.contains("\n") || field.contains("\r")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return "\"" + field + "\"";
    }
}
