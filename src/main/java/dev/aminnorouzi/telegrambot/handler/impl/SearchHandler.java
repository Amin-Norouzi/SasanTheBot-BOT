package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.movie.Movie;
import dev.aminnorouzi.telegrambot.model.Stage;
import dev.aminnorouzi.telegrambot.model.user.User;
import dev.aminnorouzi.telegrambot.service.MovieService;
import dev.aminnorouzi.telegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SearchHandler implements Handler {

    private final Set<String> cursed = Set.of("/start", "/", ",", "+", ".", ";", "-", "_", "=", "/dl_");

    private final MovieService movieService;
    private final UserService userService;

    @Override
    public boolean supports(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long userId = update.getMessage().getFrom().getId();

            User user = userService.getById(userId);
            if (user.getStage().equals(Stage.CONTACT) || user.getStage().equals(Stage.ACCOUNT_INLINE)) {
                return false;
            }

            return !cursed.contains(text) && !text.matches("/dl_\\d+");
        }

        return false;
    }

    @SneakyThrows
    @Override
    public void handle(Update update, Bot bot) {
        Long userId = update.getMessage().getFrom().getId();
        userService.changeStage(userId, Stage.SEARCH);

        String chatId = String.valueOf(update.getMessage().getChatId());
        String query = update.getMessage().getText();

        List<Movie> movies = movieService.search(query);
        movieService.save(movies);

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(getDefaultMessage(movies))
//                .replyMarkup(getDefaultKeyboard())
                .parseMode("html")
                .build();

        bot.execute(message);
    }

    private String getDefaultMessage(List<Movie> movies) {
        StringBuilder builder = new StringBuilder();
        builder.append("<b>Your search results:</b> \n\n");

        for (Movie movie : movies) {
            builder.append("<b>- </b>")
                    .append(movie.getTitle())
                    .append(" ")
                    .append(movie.getReleased().getYear())
                    .append(" <b>(")
                    .append("/dl_")
                    .append(movie.getTmdbId())
                    .append(")</b>")
                    .append("\n");
        }

        return builder.toString();
    }
}
