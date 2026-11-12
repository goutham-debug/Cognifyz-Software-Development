package com.cognifyz.level3.task6;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Robust web scraper utilizing Jsoup to extract product information,
 * handle HTML parsing, traverse pagination, and filter results.
 */
public class WebScraper {

    public static final String DEFAULT_URL = "https://books.toscrape.com/";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
            + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    private static final int TIMEOUT_MILLIS = 10000;

    /**
     * Scrapes books starting from the given URL with limit and keyword filter.
     *
     * @param startUrl Starting website URL
     * @param maxResults Maximum number of items to collect (<= 0 for all on target pages)
     * @param keywordFilter Optional title filter (null or empty for no filter)
     * @param maxPages Maximum pagination pages to traverse (e.g. 1-10)
     * @return List of extracted BookItem objects
     * @throws IOException on network or parsing failure
     */
    public List<BookItem> scrapeBooks(String startUrl, int maxResults, String keywordFilter, int maxPages) throws IOException {
        validateUrl(startUrl);

        List<BookItem> collected = new ArrayList<>();
        String currentUrl = startUrl;
        int pageCounter = 0;
        String filterLower = (keywordFilter != null && !keywordFilter.trim().isEmpty())
                ? keywordFilter.trim().toLowerCase() : null;

        System.out.printf("[SCRAPER] Connecting to: %s%n", startUrl);

        while (currentUrl != null && pageCounter < maxPages) {
            pageCounter++;
            System.out.printf("[SCRAPER] Fetching Page #%d -> %s%n", pageCounter, currentUrl);

            Document doc;
            try {
                Connection connection = Jsoup.connect(currentUrl)
                        .userAgent(USER_AGENT)
                        .timeout(TIMEOUT_MILLIS)
                        .referrer("https://www.google.com");

                doc = connection.get();
            } catch (IOException e) {
                System.err.printf("[ERROR] Failed to fetch page #%d (%s): %s%n", pageCounter, currentUrl, e.getMessage());
                if (pageCounter == 1) {
                    throw e; // Rethrow on initial page failure
                }
                break;
            }

            // Target book cards
            Elements bookArticles = doc.select("article.product_pod");
            if (bookArticles.isEmpty()) {
                System.out.println("[WARN] No product cards found on this page. Stopping pagination.");
                break;
            }

            for (Element article : bookArticles) {
                // Title & URL
                Element titleElement = article.selectFirst("h3 > a");
                String title = (titleElement != null) ? titleElement.attr("title") : "Untitled";
                if (title == null || title.isEmpty()) {
                    title = (titleElement != null) ? titleElement.text() : "Untitled";
                }
                String relProductUrl = (titleElement != null) ? titleElement.attr("href") : "";
                String productUrl = (titleElement != null) ? titleElement.absUrl("href") : "";

                // Keyword filtering
                if (filterLower != null && !title.toLowerCase().contains(filterLower)) {
                    continue;
                }

                // Price
                Element priceElement = article.selectFirst("p.price_color");
                String price = (priceElement != null) ? priceElement.text() : "N/A";

                // Rating
                Element starElement = article.selectFirst("p.star-rating");
                String rating = BookItem.normalizeRating((starElement != null) ? starElement.className() : "");

                // Availability
                Element stockElement = article.selectFirst("p.instock.availability");
                String stock = (stockElement != null) ? stockElement.text().trim() : "Unknown";

                BookItem book = new BookItem(title, price, stock, rating, productUrl);
                collected.add(book);

                if (maxResults > 0 && collected.size() >= maxResults) {
                    System.out.printf("[SCRAPER] Desired limit of %d items reached.%n", maxResults);
                    return collected;
                }
            }

            // Pagination handling: look for next link
            Element nextLink = doc.selectFirst("li.next > a");
            if (nextLink != null) {
                currentUrl = nextLink.absUrl("href");
            } else {
                currentUrl = null; // No further pages
            }

            // Politeness delay between page requests
            if (currentUrl != null && pageCounter < maxPages) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ignored) {}
            }
        }

        return collected;
    }

    /**
     * Validates that the provided URL is a syntactically valid HTTP/HTTPS URL.
     */
    public static void validateUrl(String url) throws MalformedURLException {
        if (url == null || url.trim().isEmpty()) {
            throw new MalformedURLException("URL cannot be empty.");
        }
        URL u = new URL(url.trim());
        String proto = u.getProtocol().toLowerCase();
        if (!proto.equals("http") && !proto.equals("https")) {
            throw new MalformedURLException("Only http and https protocols are supported.");
        }
    }
}
