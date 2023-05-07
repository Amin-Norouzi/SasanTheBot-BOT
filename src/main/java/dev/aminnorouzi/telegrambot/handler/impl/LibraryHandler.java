package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.movie.Movie;
import dev.aminnorouzi.telegrambot.model.Stage;
import dev.aminnorouzi.telegrambot.service.LibraryService;
import dev.aminnorouzi.telegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LibraryHandler implements Handler {

    private final LibraryService libraryService;
    private final UserService userService;

    @Override
    public boolean supports(Update update) {
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            return data.contains("library-callback");
        }

        return false;
    }

    @SneakyThrows
    @Override
    public void handle(Update update, Bot bot) {
        CallbackQuery callbackQuery = update.getCallbackQuery();

        Long userId = callbackQuery.getFrom().getId();
        Long movieId = Long.valueOf(List.of(callbackQuery.getData().split("#")).get(1));

        userService.changeStage(userId, Stage.LIBRARY);

        String text;

        Movie movie = libraryService.addToMovies(userId, movieId);
        if (movie == null) {
            text = "\uD83D\uDD34 Movie already exists in your library!";
        } else {
            text = "\uD83D\uDFE2 %s successfully added to your library!"
                    .formatted(movie.getTitle());
        }

        AnswerCallbackQuery answer = AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQuery.getId())
                .text(text)
                .build();

        bot.execute(answer);
    }
}
