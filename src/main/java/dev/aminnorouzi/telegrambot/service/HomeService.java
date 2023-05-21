package dev.aminnorouzi.telegrambot.service;

import dev.aminnorouzi.telegrambot.client.UserClient;
import dev.aminnorouzi.telegrambot.model.bot.MessageTemplate;
import dev.aminnorouzi.telegrambot.model.user.User;
import dev.aminnorouzi.telegrambot.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeService {

    private final UserClient userClient;
    private final StringUtil stringUtil;

    public SendMessage greet(Long chatId) {
        User user = userClient.getByTelegramId(chatId)
                .orElseThrow();

        String fullName = stringUtil.capitalize(user.getFullName());
        String text = MessageTemplate.HOME.getValue()
                .formatted(fullName);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getTelegramId());
        sendMessage.setText(text);
        sendMessage.setParseMode(ParseMode.HTML);

        return sendMessage;
    }
}
