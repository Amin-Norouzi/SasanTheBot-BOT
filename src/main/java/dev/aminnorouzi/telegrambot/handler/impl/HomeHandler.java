package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.bot.Command;
import dev.aminnorouzi.telegrambot.service.AuthService;
import dev.aminnorouzi.telegrambot.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class HomeHandler implements Handler {

    private final AuthService authService;
    private final HomeService homeService;

    @Override
    public boolean supports(Update update) {
        return getText(update).matches(Command.START.getPattern()) &&
                authService.isAuthenticated(getChatId(update));
    }

    @Override
    @SneakyThrows
    public void handle(Update update, Bot bot) {
        SendMessage sendMessage = homeService.greet(getChatId(update));
        bot.execute(sendMessage);
    }
}
