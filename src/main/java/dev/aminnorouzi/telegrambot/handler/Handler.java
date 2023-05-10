package dev.aminnorouzi.telegrambot.handler;

import dev.aminnorouzi.telegrambot.core.Bot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public interface Handler {

    boolean supports(Update update);

    void handle(Update update, Bot bot);

    boolean validate(String input, Object object);
}
