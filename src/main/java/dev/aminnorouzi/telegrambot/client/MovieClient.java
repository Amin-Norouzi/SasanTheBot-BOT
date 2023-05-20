package dev.aminnorouzi.telegrambot.client;

import dev.aminnorouzi.telegrambot.model.movie.Movie;
import dev.aminnorouzi.telegrambot.model.movie.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovieClient {

    private final RestTemplate restTemplate;

    @Value("${sasan.api.movie-client.search}")
    private String searchUrl;

    @Value("${sasan.api.movie-client.get-by-tmdb-id}")
    private String getByTmdbIdUrl;

    public List<Search> search(String query) {
        try {
            ResponseEntity<Search[]> response = restTemplate
                    .getForEntity(searchUrl.formatted(query), Search[].class);

            if (response.getBody() != null && response.getBody().length != 0) {
                return List.of(response.getBody());
            }
        } catch (RuntimeException exception) {
            log.error(exception.getMessage());
        }

        return List.of();
    }

    public Optional<Movie> getByTmdbId(Long tmdbId, String type) {
        try {
            ResponseEntity<Movie> response = restTemplate
                    .postForEntity(getByTmdbIdUrl.formatted(tmdbId, type), null, Movie.class);

            return Optional.ofNullable(response.getBody());
        } catch (RuntimeException exception) {
            log.error(exception.getMessage());
        }

        return Optional.empty();
    }
}
