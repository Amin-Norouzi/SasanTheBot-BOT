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
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountHandler implements Handler {

    private final LibraryService libraryService;
    private final UserService userService;

    @Override
    public boolean supports(Update update) {
        if (update.hasInlineQuery()) {
            String query = update.getInlineQuery().getQuery();
            return query.equals("movies") || query.equals("downloads");
        }

        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            return data.equalsIgnoreCase("account-callback") ||
                    data.equalsIgnoreCase("user-library-callback");
        }

        return false;
    }

    @SneakyThrows
    @Override
    public void handle(Update update, Bot bot) {
        if (update.hasInlineQuery()) {
            InlineQuery inlineQuery = update.getInlineQuery();
            String query = inlineQuery.getQuery();
            Long userId = inlineQuery.getFrom().getId();

            userService.changeStage(userId, Stage.ACCOUNT_INLINE);

            if (query.equals("movies")) {
                AnswerInlineQuery answer = new AnswerInlineQuery();
                answer.setInlineQueryId(update.getInlineQuery().getId());
                answer.setResults(getMoviesInlineQueryResults(inlineQuery));

                bot.execute(answer);
            }
            if (query.equals("downloads")) {
                AnswerInlineQuery answer = new AnswerInlineQuery();
                answer.setInlineQueryId(update.getInlineQuery().getId());
                answer.setResults(getDownloadsInlineQueryResults(inlineQuery));

                bot.execute(answer);
            }
        }

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            bot.execute(getDefaultAnswer(callbackQuery));

            Long userId = callbackQuery.getFrom().getId();
            String chatId = String.valueOf(callbackQuery.getMessage().getChatId());

            userService.changeStage(userId, Stage.ACCOUNT);

//        if (callbackQuery.getData().equalsIgnoreCase("user-library-callback")) {
//            Message message = messages.get(userId);
//
//            EditMessageText editMessage = EditMessageText.builder()
//                    .chatId(chatId)
//                    .messageId(message.getMessageId())
//                    .text("The message has been edited!")
//                    .build();
//
//            bot.execute(editMessage);
//
//            messages.remove(userId);
//            return;
//        }
            String username = callbackQuery.getFrom().getUserName();

            SendMessage sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(getDefaultMessage(username))
                    .replyMarkup(getDefaultKeyboard())
                    .parseMode("html")
                    .build();

            bot.execute(sendMessage);
        }
    }

    private List<InlineQueryResult> getMoviesInlineQueryResults(InlineQuery inlineQuery) {
        List<InlineQueryResult> results = new ArrayList<>();

        Long userId = inlineQuery.getFrom().getId();

        List<Movie> movies = libraryService.getMovies(userId);
        movies.forEach(movie -> {
            InlineQueryResultArticle result = new InlineQueryResultArticle();
            result.setId(String.valueOf(movie.getTmdbId()));
            result.setTitle(movie.getTitle());
            result.setDescription(movie.getOverview());
            result.setThumbUrl(movie.getPoster());
            InputTextMessageContent content = new InputTextMessageContent(
                    "Hey, I have %s in my library.".formatted(movie.getTitle()));
            result.setInputMessageContent(content);

            results.add(result);
        });

        return results;
    }

    private List<InlineQueryResult> getDownloadsInlineQueryResults(InlineQuery inlineQuery) {
        List<InlineQueryResult> results = new ArrayList<>();

        Long userId = inlineQuery.getFrom().getId();

        List<Movie> movies = libraryService.getDownloads(userId);
        movies.forEach(movie -> {
            InlineQueryResultArticle result = new InlineQueryResultArticle();
            result.setId(String.valueOf(movie.getTmdbId()));
            result.setTitle(movie.getTitle());
            result.setDescription(movie.getOverview());
            result.setThumbUrl(movie.getPoster());
            InputTextMessageContent content = new InputTextMessageContent(
                    "Hey, I downloaded %s recently.".formatted(movie.getTitle()));
            result.setInputMessageContent(content);

            results.add(result);
        });

        return results;
    }

    private ReplyKeyboard getDefaultKeyboard() {
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                                List.of(InlineKeyboardButton.builder()
                                        .text("My Library")
//                                        .callbackData("user-library-callback")
                                        .switchInlineQueryCurrentChat("movies")
                                        .build()),
                                List.of(InlineKeyboardButton.builder()
                                        .text("Downloads")
//                                        .callbackData("user-download-callback")
                                        .switchInlineQueryCurrentChat("downloads")
                                        .build()),
                                List.of(InlineKeyboardButton.builder()
                                        .text("Connect to MovieStalker \uD83C\uDF7F")
                                        .callbackData("connection-callback")
                                        .build())
                        )
                ).build();
    }

    private String getDefaultMessage(String username) {
        return ("<b>@%s welcome back to your account. What do you want to do in your settings?</b>")
                .formatted(username);
    }

    private AnswerCallbackQuery getDefaultAnswer(CallbackQuery callbackQuery) {
        return AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQuery.getId())
//                .text("You can not see your account yet honey. \uD83D\uDE18")
//                .showAlert(true)
                .build();
    }
}
