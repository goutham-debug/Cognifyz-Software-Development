package com.cognifyz.level3.task6;

/**
 * Model representing an extracted book item from the target web store.
 */
public class BookItem {

    private final String title;
    private final String price;
    private final String availability;
    private final String rating;
    private final String productUrl;

    public BookItem(String title, String price, String availability, String rating, String productUrl) {
        this.title = title != null ? title.trim() : "";
        this.price = price != null ? price.trim() : "";
        this.availability = availability != null ? availability.trim() : "";
        this.rating = rating != null ? rating.trim() : "";
        this.productUrl = productUrl != null ? productUrl.trim() : "";
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getAvailability() {
        return availability;
    }

    public String getRating() {
        return rating;
    }

    public String getProductUrl() {
        return productUrl;
    }

    /**
     * Converts word ratings (e.g. "Three") into numeric/star representations.
     */
    public static String normalizeRating(String classNames) {
        if (classNames == null) return "N/A";
        String lower = classNames.toLowerCase();
        if (lower.contains("five")) return "★★★★★ (5/5)";
        if (lower.contains("four")) return "★★★★☆ (4/5)";
        if (lower.contains("three")) return "★★★☆☆ (3/5)";
        if (lower.contains("two")) return "★★☆☆☆ (2/5)";
        if (lower.contains("one")) return "★☆☆☆☆ (1/5)";
        return "Not Rated";
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s", title, price, rating, availability);
    }
}
