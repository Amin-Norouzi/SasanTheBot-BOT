package dev.aminnorouzi.telegrambot.service;

import dev.aminnorouzi.telegrambot.client.MovieClient;
import dev.aminnorouzi.telegrambot.database.TempInMemoryMovieDatabase;
import dev.aminnorouzi.telegrambot.model.movie.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieClient movieClient;

    public List<Movie> search(String query) {
        return movieClient.search(query);
    }

    public void save(Movie movie) {
        TempInMemoryMovieDatabase.add(movie.getTmdbId(), movie);
    }

    public void save(List<Movie> movies) {
        movies.forEach(this::save);
    }

    public Movie getById(Long id) {
        return TempInMemoryMovieDatabase.find(id);
    }
}
