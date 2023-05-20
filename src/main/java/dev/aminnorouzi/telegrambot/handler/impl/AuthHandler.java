package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.client.UserClient;
import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AuthHandler implements Handler {

    private final UserClient userClient;

    @Override
    public boolean supports(Update update) {
        Long userTgId = update.getMessage().getFrom().getId();

        return userClient.getByTelegramId(userTgId).isEmpty();
    }

    @Override
    @SneakyThrows
    public void handle(Update update, Bot bot) {
        Long userTgId = update.getMessage().getFrom().getId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userTgId);
        sendMessage.setText("You should authenticate first! (But you cannot hehe)");
        sendMessage.setParseMode(ParseMode.HTML);

        bot.execute(sendMessage);
    }
}
