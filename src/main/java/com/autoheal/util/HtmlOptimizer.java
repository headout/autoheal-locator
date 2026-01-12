package com.autoheal.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Set;

public class HtmlOptimizer {

    private static final Set<String> REMOVE_TAGS = Set.of(
            "script", "style", "meta", "link", "svg", "canvas", "noscript"
    );

    private static final Set<String> KEEP_ATTRS = Set.of(
            "id", "name", "type", "placeholder", "value","class","data-qa-marker","data-testid",
            "role", "aria-label", "aria-labelledby",
            "href", "for"
    );

    public static String optimize(String html) {
        Document doc = Jsoup.parse(html);

        // Remove unwanted tags
        for (String tag : REMOVE_TAGS) {
            doc.select(tag).remove();
        }

        // Clean attributes
        for (Element el : doc.getAllElements()) {
            el.attributes().asList().removeIf(
                    attr -> !KEEP_ATTRS.contains(attr.getKey())
            );
        }

        // Remove empty elements
        doc.select("*").removeIf(
                el -> el.children().isEmpty()
                        && el.text().trim().isEmpty()
                        && el.attributes().isEmpty()
        );

        return doc.body().html();
    }
}

