package dev.aminnorouzi.telegrambot.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {

    private static final int SHORT_STRING_LENGTH = 25;

    public Long extractMovieTmdbId(String command) {
        String value = command.replaceAll("\\D+", "");

        return Long.valueOf(value);
    }

    public String extractMovieType(String command) {
        String value = command.split("_")[1];

        String prefix = value.replaceAll("\\d+", "");
        if (prefix.equals("m")) {
            return "movie";
        }
        return "tv";
    }

    public String shorten(String str) {
        String[] words = str.split("\\s+");
        if (words.length <= SHORT_STRING_LENGTH) {
            return str;
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < SHORT_STRING_LENGTH; i++) {
                builder.append(words[i]).append(" ");
            }
            return builder.toString()
                    .trim() + "...";
        }
    }

    public String capitalize(String str) {
        StringBuilder builder = new StringBuilder();

        String[] words = str.split("\\s");
        for (String word : words) {
            String capitalized = word.substring(0, 1).toUpperCase() + word.substring(1);

            builder.append(capitalized)
                    .append(" ");
        }

        return builder.toString()
                .trim();
    }
}
