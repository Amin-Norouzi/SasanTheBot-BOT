package dev.aminnorouzi.telegrambot.service;

import dev.aminnorouzi.telegrambot.model.movie.Movie;
import dev.aminnorouzi.telegrambot.model.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryService {

    private final UserService userService;
    private final MovieService movieService;

    public Movie addToMovies(Long userId, Long movieId) {
        User user = userService.getById(userId);
        Movie movie = movieService.getById(movieId);

        if (!user.getMovies().contains(movie)) {
            user.getMovies().add(movie);
            return movie;
        }

        return null;
    }

    public Movie addToDownloads(Long userId, Long movieId) {
        User user = userService.getById(userId);
        Movie movie = movieService.getById(movieId);

        if (!user.getDownloads().contains(movie)) {
            user.getDownloads().add(movie);
        }

        return movie;
    }

    public List<Movie> getMovies(Long userId) {
        return userService.getById(userId)
                .getMovies();
    }

    public List<Movie> getDownloads(Long userId) {
        return userService.getById(userId)
                .getDownloads();
    }
}
