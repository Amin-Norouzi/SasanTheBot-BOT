package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.bot.Command;
import dev.aminnorouzi.telegrambot.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class SearchHandler implements Handler {

    private final SearchService searchService;

    @Override
    public boolean supports(Update update) {
        String text = getText(update);

        return !text.matches(Command.START.getPattern()) &&
                !text.matches(Command.DOWNLOAD.getPattern());
    }

    @Override
    @SneakyThrows
    public void handle(Update update, Bot bot) {
        SendMessage sendMessage = searchService.search(getText(update), getChatId(update));
        bot.execute(sendMessage);
    }
}
