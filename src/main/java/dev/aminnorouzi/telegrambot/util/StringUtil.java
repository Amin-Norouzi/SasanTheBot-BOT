package dev.aminnorouzi.telegrambot.util;

import dev.aminnorouzi.telegrambot.model.bot.MessageTemplate;
import dev.aminnorouzi.telegrambot.model.download.Download;
import dev.aminnorouzi.telegrambot.model.download.Link;
import dev.aminnorouzi.telegrambot.model.movie.Genre;
import dev.aminnorouzi.telegrambot.model.movie.Movie;
import dev.aminnorouzi.telegrambot.model.movie.Search;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public String generateDownloadCommand(Search search) {
        String command;
        if (search.getMediaType().equals("movie")) {
            command = "m" + search.getTmdbId();
        } else {
            command = "t" + search.getTmdbId();
        }
        return "/dl_" + command;
    }

    public String generateMovieGenres(Movie movie) {
        StringBuilder builder = new StringBuilder();
        int count = 1;
        for (Genre genre : movie.getGenres()) {
            if (count == 3) break;
            String name = genre.getName().replaceAll("\\s", "");
            builder.append("#").append(name).append(" ");

            count++;
        }

        return builder.toString();
    }

    public String generateMovieCaption(Movie movie, String info) {
        String genres = generateMovieGenres(movie);
        String title = movie.getTitle();
        int released = movie.getReleased().getYear();
        String runtime = movie.getRuntime();
        double rating = movie.getRating();
        String overview = shorten(movie.getOverview());

        return MessageTemplate.DOWNLOAD.getValue()
                .formatted(genres, title, released, runtime, rating, overview, info);
    }

    public String generateMovieCaption(Movie movie) {
        return generateMovieCaption(movie, "");
    }

    public String generateMovieCaptionWithLinks(Download download) {
        StringBuilder builder = new StringBuilder();

        String info = MessageTemplate.DOWNLOAD_INFO.getValue()
                .formatted(download.getUrl(), "Download links are available.");
        builder.append(info);

        for (Link link : download.getLinks()) {
            String linkText = MessageTemplate.DOWNLOAD_LINKS.getValue()
                    .formatted(link.getQuality(), link.getSize(), link.getUrl());
            builder.append(linkText);
        }

        return builder.toString();
    }

    public String generateSearchText(List<Search> result) {
        StringBuilder builder = new StringBuilder();

        int count = 1;
        for (Search search : result) {
            String command = generateDownloadCommand(search);
            String title = search.getTitle();
            int released = search.getReleased().getYear();

            builder.append(MessageTemplate.SEARCH_MOVIES.getValue()
                    .formatted(count, title, released, command)
            );

            count++;
        }

        return MessageTemplate.SEARCH.getValue()
                .formatted(builder.toString());
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
