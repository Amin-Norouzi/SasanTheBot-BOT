package dev.aminnorouzi.telegrambot.core;

import dev.aminnorouzi.telegrambot.handler.impl.DefaultHandler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Bot extends TelegramLongPollingBot {

    private final DefaultHandler handler;

    // maybe useless ?
    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.username}")
    private String username;

    public Bot(String botToken, DefaultHandler handler) {
        super(botToken);
        this.handler = handler;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (handler.supports(update)) {
            handler.handle(update, this);
        }
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }
}
