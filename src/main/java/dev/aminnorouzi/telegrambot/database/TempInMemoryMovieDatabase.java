package dev.aminnorouzi.telegrambot.database;

import dev.aminnorouzi.telegrambot.model.movie.Movie;

import java.util.HashMap;
import java.util.Map;

public class TempInMemoryMovieDatabase {

    public static final Map<Long, Movie> movies = new HashMap<>();

    public static void add(Long id, Movie movie) {
        movies.putIfAbsent(id, movie);
    }

    public static Movie find(Long id) {
        return movies.get(id);
    }

    public static void remove(Long id) {
        movies.remove(id);
    }
}
