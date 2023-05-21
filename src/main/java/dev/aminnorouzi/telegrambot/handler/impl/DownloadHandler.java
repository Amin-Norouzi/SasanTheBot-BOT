package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.client.DownloadClient;
import dev.aminnorouzi.telegrambot.client.MovieClient;
import dev.aminnorouzi.telegrambot.client.UserClient;
import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.bot.Command;
import dev.aminnorouzi.telegrambot.service.DownloadService;
import dev.aminnorouzi.telegrambot.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class DownloadHandler implements Handler {

    private final DownloadService downloadService;
    private final MovieClient movieClient;
    private final StringUtil stringUtil;
    private final UserClient userClient;
    private final DownloadClient downloadClient;

    @Override
    public boolean supports(Update update) {
        return getText(update).matches(Command.DOWNLOAD.getPattern());
    }

    @Override
    @SneakyThrows
    public void handle(Update update, Bot bot) {
        String command = getText(update);
        Long chatId = getChatId(update);

        SendPhoto sendPhoto = downloadService.inform(command, chatId);
        Message message = bot.execute(sendPhoto);

        if (!downloadService.supports(command)) {
            return;
        }

        EditMessageCaption editMessage = downloadService.download(command, chatId, message.getMessageId());
        bot.execute(editMessage);
    }
}
