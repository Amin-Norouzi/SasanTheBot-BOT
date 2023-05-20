package dev.aminnorouzi.telegrambot.model.movie;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Search {

    @JsonProperty("id")
    private Long tmdbId;

    @JsonProperty("media_type")
    private String mediaType;

    @JsonAlias({"name", "title"})
    private String title;

    @ToString.Exclude
    @JsonProperty("overview")
    private String overview;

    @JsonProperty("poster_path")
    private String poster;

    @JsonProperty("backdrop_path")
    private String backdrop;

    @JsonAlias({"release_date", "first_air_date"})
    private LocalDate released;
}
