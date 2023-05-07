package dev.aminnorouzi.telegrambot.model.user;

import dev.aminnorouzi.telegrambot.model.Stage;
import dev.aminnorouzi.telegrambot.model.movie.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String chatId;
    private String username;
    private String firstName;
    private String lastName;
    private Stage stage;
    private LocalDateTime joinedIn;
    private List<Movie> movies;
    private List<Movie> downloads;
}
