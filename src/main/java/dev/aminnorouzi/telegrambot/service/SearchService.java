package dev.aminnorouzi.telegrambot.service;


import dev.aminnorouzi.telegrambot.client.MovieClient;
import dev.aminnorouzi.telegrambot.model.bot.MessageTemplate;
import dev.aminnorouzi.telegrambot.model.movie.Search;
import dev.aminnorouzi.telegrambot.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final MovieClient movieClient;
    private final StringUtil stringUtil;

    public SendMessage search(String query, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.HTML);

        List<Search> result = movieClient.search(query);
        if (!result.isEmpty()) {
            String text = stringUtil.generateSearchText(result);

            sendMessage.setText(text);
        } else {
            sendMessage.setText("The '%s' was not found!".formatted(query));
        }

        return sendMessage;
    }
}
