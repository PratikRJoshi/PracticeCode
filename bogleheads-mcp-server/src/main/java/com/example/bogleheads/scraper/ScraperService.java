package com.example.bogleheads.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for discovering forum thread URLs from the bogleheads.org index pages.
 * <p>
 * NOTE: This is an initial implementation meant for a small proof-of-concept crawl.  In production
 * you should add polite crawling practices (rate-limiting, retry-with-backoff, user-agent header,
 * distributed queue, etc.) and unit tests that stub HTTP calls.
 */
@Service
public class ScraperService {

    private static final String INDEX_URL = "https://www.bogleheads.org/forum/index.php";
    private static final String BASE_URL = "https://www.bogleheads.org/forum/";
    private static final int TIMEOUT_MS = (int) Duration.ofSeconds(30).toMillis();

    /**
     * Crawl the forum index and return discovered thread URLs.
     *
     * @param topicPagesPerForum How many topic pages to crawl per forum (100 topics per page).
     * @return List of absolute thread URLs.
     * @throws IOException if network or parsing fails.
     */
    public List<String> crawlIndex(int topicPagesPerForum) throws IOException {
        List<String> threads = new ArrayList<>();

        // Step 1: fetch the main index and collect forum links
        Document indexDoc = Jsoup.connect(INDEX_URL)
                                 .userAgent("Mozilla/5.0 (compatible; bogleheads-mcp/1.0; +https://github.com/your-repo)")
                                 .timeout(TIMEOUT_MS)
                                 .get();
        List<String> forumUrls = extractForumUrls(indexDoc);

        // Step 2: iterate through each forum and collect topic links
        for (String forumUrl : forumUrls) {
            for (int page = 0; page < topicPagesPerForum; page++) {
                String pagedUrl = forumUrl + "&start=" + (page * 100);
                Document forumDoc = Jsoup.connect(pagedUrl)
                                          .userAgent("Mozilla/5.0 (compatible; bogleheads-mcp/1.0; +https://github.com/your-repo)")
                                          .timeout(TIMEOUT_MS)
                                          .get();
                threads.addAll(extractThreadUrls(forumDoc));
            }
        }
        return threads;
    }

    private List<String> extractThreadUrls(Document doc) {
        return doc.select("a.topictitle")
                  .stream()
                  .map(el -> "https://www.bogleheads.org/forum/" + el.attr("href"))
                  .toList();
    }

    private List<String> extractForumUrls(Document doc) {
        return doc.select("a.forumtitle")
                  .stream()
                  .map(el -> el.attr("href"))
                  .map(href -> href.startsWith("./") ? href.substring(2) : href) // strip leading ./
                  .filter(href -> href.startsWith("viewforum.php?f=")) // keep only forum pages
                  .map(href -> BASE_URL + href)
                  .distinct()
                  .toList();
    }
}
