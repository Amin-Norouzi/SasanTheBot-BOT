package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.client.MovieClient;
import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.bot.Command;
import dev.aminnorouzi.telegrambot.model.movie.Search;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchHandler implements Handler {

    private final MovieClient movieClient;

    @Override
    public boolean supports(Update update) {
        String text = update.getMessage().getText();

        return !text.matches(Command.START.getPattern()) &&
                !text.matches(Command.DOWNLOAD.getPattern());
    }

    @Override
    @SneakyThrows
    public void handle(Update update, Bot bot) {
        String query = update.getMessage().getText();
        Long userTgId = update.getMessage().getFrom().getId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userTgId);
        sendMessage.setParseMode(ParseMode.HTML);

        List<Search> result = movieClient.search(query);
        if (!result.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("<b>Your search results:</b> \n\n");

            int count = 1;
            for (Search search : result) {
                String command;
                if (search.getMediaType().equals("movie")) {
                    command = "m" + search.getTmdbId();
                } else {
                    command = "t" + search.getTmdbId();
                }

                builder.append(count)
                        .append(". ")
                        .append(search.getTitle())
                        .append(" ")
                        .append(search.getReleased().getYear())
                        .append(" (")
                        .append("/dl_")
                        .append(command)
                        .append(")")
                        .append("\n");

                count++;
            }

            builder.append("\n<b>Please hit a command of a movie/tv to download it.</b>");
            sendMessage.setText(builder.toString());
        } else {
            sendMessage.setText("The %s was not found!".formatted(query));
        }

        bot.execute(sendMessage);
    }
}
