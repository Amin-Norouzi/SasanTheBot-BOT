package dev.aminnorouzi.telegrambot.client;

import dev.aminnorouzi.telegrambot.model.movie.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// TODO port this
@FeignClient(value = "movie-service", url = "localhost:8080/api/v1/movies")
public interface MovieClient {

    @GetMapping("/search?q={query}")
    List<Movie> search(@PathVariable String query);

//    @GetMapping("${movie.client.api.get-find}")
//    Search.SearchResponse find(@PathVariable("imdbId") String imdbId);
//
//    @GetMapping("${movie.client.api.get-movie}")
//    TmdbMovie get(@PathVariable("tmdbId") Long tmdbId, @PathVariable("type") String type);
}
