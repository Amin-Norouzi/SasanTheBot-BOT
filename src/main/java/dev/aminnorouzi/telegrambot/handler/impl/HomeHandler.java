package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.client.UserClient;
import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.bot.Command;
import dev.aminnorouzi.telegrambot.model.user.User;
import dev.aminnorouzi.telegrambot.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class HomeHandler implements Handler {

    private final UserClient userClient;
    private final StringUtil stringUtil;

    @Override
    public boolean supports(Update update) {
        Long userTgId = update.getMessage().getFrom().getId();
        String text = update.getMessage().getText();

        return text.matches(Command.START.getPattern()) &&
                userClient.getByTelegramId(userTgId).isPresent();
    }

    @Override
    @SneakyThrows
    public void handle(Update update, Bot bot) {
        Long tgId = update.getMessage().getFrom().getId();
        User user = userClient.getByTelegramId(tgId).get();

        String text = ("<b>Hi %s, I'm Sasan known as the bot. \uD83D\uDC4B\uD83C\uDFFB \nYou wanna download " +
                "a movie/tv? Just say its name... I'm all ears.</b>")
                .formatted(stringUtil.capitalize(user.getFullName()));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getTelegramId());
        sendMessage.setText(text);
        sendMessage.setParseMode(ParseMode.HTML);

        bot.execute(sendMessage);
    }
}
