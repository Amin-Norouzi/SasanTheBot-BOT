package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.movie.Movie;
import dev.aminnorouzi.telegrambot.model.Stage;
import dev.aminnorouzi.telegrambot.model.user.User;
import dev.aminnorouzi.telegrambot.service.LibraryService;
import dev.aminnorouzi.telegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DownloadHandler implements Handler {

    private final LibraryService libraryService;
    private final UserService userService;

    @Override
    public boolean supports(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long userId = update.getMessage().getFrom().getId();

            User user = userService.getById(userId);

            if (user.getStage().equals(Stage.CONTACT) || user.getStage().equals(Stage.SEARCH)) {
                return false;
            }

            return text.matches("/dl_\\d+") ||
                    text.matches("/start\\s\\d+");
        }

        return false;
    }

    @SneakyThrows
    @Override
    public void handle(Update update, Bot bot) {
        if (update.hasMessage()) {
            Message message = update.getMessage();

            Long movieId;
            if (message.getText().contains("/dl")) {
                movieId = Long.valueOf(List.of(message.getText().split("_")).get(1));
            } else {
                movieId = Long.valueOf(List.of(message.getText().split("\\s")).get(1));
            }

            String chatId = String.valueOf(message.getChatId());
            Long userId = message.getFrom().getId();

            userService.changeStage(userId, Stage.DOWNLOAD);

            Movie movie = libraryService.addToDownloads(userId, movieId);

            SendPhoto sendPhoto = SendPhoto.builder()
                    .chatId(chatId)
                    .photo(getDefaultPhoto(movie))
                    .caption(getDefaultCaption(movie))
                    .replyMarkup(getDefaultKeyboard(movie))
                    .parseMode("html")
                    .build();

            bot.execute(sendPhoto);
        }
    }

    private ReplyKeyboard getDefaultKeyboard(Movie movie) {
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                                List.of(
                                        InlineKeyboardButton.builder()
                                                .text("Add to Library")
                                                .callbackData("library-callback#%s"
                                                        .formatted(movie.getTmdbId()))
                                                .build()
                                )
                        )
                ).build();
    }

    private InputFile getDefaultPhoto(Movie movie) {
        return new InputFile(movie.getPoster());
    }

    private String getDefaultCaption(Movie movie) {
        return "<b>Title: </b>" +
                movie.getTitle() +
                "\n" +
                "<b>Released: </b>" +
                movie.getReleased() +
                "\n" +
                "<b>Type: </b>" +
                movie.getMediaType().toUpperCase() +
                "\n" +
                "<b>Overview: </b>" +
                movie.getOverview();
    }
}