package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DefaultHandler implements Handler {

    private final Set<Handler> handlers;
    private final UserService userService;

    @Override
    public boolean supports(Update update) {
        return true;
    }

    @Override
    public void handle(Update update, Bot bot) {
        userService.findOrSaveIfNotExists(update);

        handlers.stream()
                .filter(handler -> handler.supports(update))
                .findFirst()
                .ifPresent(handler -> handler.handle(update, bot));
    }
}