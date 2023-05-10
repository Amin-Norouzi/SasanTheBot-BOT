package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UpdateHandler implements Handler {

    private final Set<Handler> handlers;

    @Override
    public boolean supports(Update update) {
        return (update.hasMessage() && update.getMessage().hasText()) ||
                update.hasCallbackQuery() ||
                update.hasInlineQuery();
    }

    @Override
    public void handle(Update update, Bot bot) {
        handlers.stream()
                .filter(handler -> handler.supports(update))
                .findFirst()
                .ifPresent(handler -> handler.handle(update, bot));
    }

    @Override
    public boolean validate(String input, Object object) {
        return false;
    }
}
