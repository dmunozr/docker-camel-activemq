package com.demo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSearchUtil {

    private static final Logger LOG = LoggerFactory.getLogger(WebSearchUtil.class);

    private static final short MAX_ATTEMPTS = 10;

    private static final String DOG_PILE_SEARCH_IMAGE_URL_MASK = "http://results.dogpile.com/serp?qc=images&q=%s";

    private static final Map<String, String> RESULTS_CACHE = new HashMap<>();

    /**
     * This waiting time is required due to the DogPile endpoint returns no results for just same queries invoked pretty much at the same time.
     */
    private static final long PERIOD_OF_TIME_BETWEEN_ATTEMPTS = 5000;

    public static String searchImage(final String query) throws IOException, InterruptedException {
        String imageUrl = RESULTS_CACHE.get(query);
        if (imageUrl == null) {
            for (int i = 0; i < MAX_ATTEMPTS && StringUtils.isBlank(imageUrl); i++) {
                LOG.debug("Searching an imageUrl for the query '{}'. Attempt {}", query, i + 1);
                if (i > 0) {
                    Thread.sleep(PERIOD_OF_TIME_BETWEEN_ATTEMPTS);
                }
                imageUrl = internalSearchImage(query);
            }
            if (StringUtils.isBlank(imageUrl)) {
                LOG.warn("No image was found even after {} attempts!", MAX_ATTEMPTS);
            } else {
                RESULTS_CACHE.put(query, imageUrl);
            }
        }

        return imageUrl;
    }

    private static String internalSearchImage(final String query) throws IOException {
        final String searchURL = String.format(DOG_PILE_SEARCH_IMAGE_URL_MASK, query);
        final Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
        final Elements results = doc.select("a.link");

        String imageUrl = null;
        for (final Element result : results) {
            imageUrl = result.attr("href");
            LOG.debug("Image url found: {}", imageUrl);
            break;
        }
        if (imageUrl == null) {
            LOG.warn("No image was found for the search: {}", searchURL);
        }

        return imageUrl;
    }
}
