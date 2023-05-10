package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.controller.impl.AccountController;
import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.bot.InlineQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class InlineQueryHandler implements Handler {

    private final AccountController accountController;

    @Override
    public boolean supports(Update update) {
        return update.hasInlineQuery();
    }

    @Override
    public void handle(Update update, Bot bot) {
        String data = update.getInlineQuery().getQuery();

        if (validate(data, InlineQuery.MOVIES)) {
            accountController.execute(update, bot);
        } else if (validate(data, InlineQuery.DOWNLOADS)) {
            accountController.execute(update, bot);
        }
    }

    @Override
    public boolean validate(String data, Object object) {
        InlineQuery inlineQuery = (InlineQuery) object;
        return data.equals(inlineQuery.getQuery());
    }
}
