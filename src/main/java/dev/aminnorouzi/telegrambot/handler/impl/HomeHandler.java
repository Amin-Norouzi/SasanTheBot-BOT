package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.Stage;
import dev.aminnorouzi.telegrambot.model.user.User;
import dev.aminnorouzi.telegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HomeHandler implements Handler {

    private final UserService userService;

    @Override
    public boolean supports(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            return text.equals("/start");
        }

        return false;
    }

    @SneakyThrows
    @Override
    public void handle(Update update, Bot bot) {
        User user = userService.findOrSaveIfNotExists(update);
        userService.changeStage(user.getId(), Stage.HOME);

        SendMessage message = SendMessage.builder()
                .chatId(user.getChatId())
                .text(getDefaultMessage(user.getUsername()))
                .replyMarkup(getDefaultKeyboard())
                .parseMode("html")
                .build();

        bot.execute(message);
    }

    private ReplyKeyboard getDefaultKeyboard() {
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                                List.of(
                                        InlineKeyboardButton.builder()
                                                .text("My Account")
                                                .callbackData("account-callback")
                                                .build(),
                                        InlineKeyboardButton.builder()
                                                .text("Contact Admin")
                                                .callbackData("contact-callback")
                                                .build()
                                )
                        )
                ).build();
    }

    private String getDefaultMessage(String username) {
        return ("<b>\uD83D\uDC4B\uD83C\uDFFB Hello, @%s. You can select a button bellow " +
                "or search a movie/tv title. \n\nFor example:</b> <code>The Batman</code> or <code>Hannibal</code>")
                .formatted(username);
    }
}
