package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.controller.impl.DownloadController;
import dev.aminnorouzi.telegrambot.controller.impl.HomeController;
import dev.aminnorouzi.telegrambot.controller.impl.SearchController;
import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.bot.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class MessageHandler implements Handler {

    private final HomeController homeController;
    private final DownloadController downloadController;
    private final SearchController searchController;

    @Override
    public boolean supports(Update update) {
        return update.hasMessage();
    }

    @Override
    public void handle(Update update, Bot bot) {
        String input = update.getMessage().getText();

        if (validate(input, Command.START)) {
            homeController.execute(update, bot);
        } else if (validate(input, Command.DOWNLOAD)) {
            downloadController.execute(update, bot);
        } else {
            searchController.execute(update, bot);
        }
    }

    @Override
    public boolean validate(String input, Object object) {
        Command command = (Command) object;

        String[] patterns = command.getPatterns();
        for (String p : patterns) {
            if (input.matches(p)) return true;
        }

        return false;
    }
}
