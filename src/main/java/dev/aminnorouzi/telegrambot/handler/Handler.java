package dev.aminnorouzi.telegrambot.handler;

import dev.aminnorouzi.telegrambot.core.Bot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public interface Handler {

    boolean supports(Update update);

    void handle(Update update, Bot bot);

    default Long getChatId(Update update) {
        return update.getMessage().getChatId();
    }

    default String getText(Update update) {
        return update.getMessage().getText();
    }
}
