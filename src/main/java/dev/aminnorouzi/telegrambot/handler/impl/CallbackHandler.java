package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.controller.impl.AccountController;
import dev.aminnorouzi.telegrambot.controller.impl.LibraryController;
import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.bot.Callback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CallbackHandler implements Handler {

    private final AccountController accountController;
    private final LibraryController libraryController;

    @Override
    public boolean supports(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    public void handle(Update update, Bot bot) {
        String query = update.getCallbackQuery().getData();

        if (validate(query, Callback.ACCOUNT)) {
            accountController.execute(update, bot);
        } else if (validate(query, Callback.LIBRARY)) {
            libraryController.execute(update, bot);
        }
    }

    @Override
    public boolean validate(String query, Object object) {
        Callback callback = (Callback) object;

        return query.equals(callback.getValue()) ||
                query.matches(callback.getPattern());
    }
}
