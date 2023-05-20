package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.client.DownloadClient;
import dev.aminnorouzi.telegrambot.client.MovieClient;
import dev.aminnorouzi.telegrambot.client.UserClient;
import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.bot.Command;
import dev.aminnorouzi.telegrambot.model.download.Download;
import dev.aminnorouzi.telegrambot.model.download.DownloadRequest;
import dev.aminnorouzi.telegrambot.model.download.Link;
import dev.aminnorouzi.telegrambot.model.movie.Genre;
import dev.aminnorouzi.telegrambot.model.movie.Movie;
import dev.aminnorouzi.telegrambot.model.movie.Type;
import dev.aminnorouzi.telegrambot.model.user.User;
import dev.aminnorouzi.telegrambot.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DownloadHandler implements Handler {

    private final MovieClient movieClient;
    private final StringUtil stringUtil;
    private final UserClient userClient;
    private final DownloadClient downloadClient;

    @Override
    public boolean supports(Update update) {
        String text = update.getMessage().getText();

        return text.matches(Command.DOWNLOAD.getPattern());
    }

    @Override
    @SneakyThrows
    public void handle(Update update, Bot bot) {
        String command = update.getMessage().getText();
        Long userTgId = update.getMessage().getFrom().getId();

        String type = stringUtil.extractMovieType(command);
        Long tmdbId = stringUtil.extractMovieTmdbId(command);

        Optional<Movie> movieOptional = movieClient.getByTmdbId(tmdbId, type);
        Optional<User> userOptional = userClient.getByTelegramId(userTgId);

        if (movieOptional.isEmpty() || userOptional.isEmpty()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(userTgId);
            sendMessage.setText("Something went wrong on our end!");
            sendMessage.setParseMode(ParseMode.HTML);

            bot.execute(sendMessage);
            return;
        }

        Movie movie = movieOptional.get();
        User user = userOptional.get();

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(userTgId);
        sendPhoto.setParseMode(ParseMode.HTML);
        sendPhoto.setPhoto(new InputFile(movie.getPoster()));

        // CAPTION SECTION - BEGIN
        StringBuilder builder = new StringBuilder();
        int limit = 1;
        for (Genre genre : movie.getGenres()) {
            if (limit == 3) break;
            String genreName = genre.getName().replaceAll("\\s", "");
            builder.append("#").append(genreName).append(" ");

            limit++;
        }

        builder.append("\n\n<b>").append(movie.getTitle())
                .append(" ")
                .append(movie.getReleased().getYear())
                .append("</b>");
        builder.append("\n").append(movie.getRuntime())
                .append(" - ").append(movie.getRating());
        builder.append("\n\n").append(stringUtil.shorten(movie.getOverview()));

        String caption = builder.toString();

        String downloadSectionText = "\n\n<b>";
        if (movie.getType().equals(Type.MOVIE)) {
            downloadSectionText += "\uD83D\uDD04 Searching for download links...</b>";
        } else {
            downloadSectionText += "⚠️ TV shows are not available for now.</b>";
        }

        sendPhoto.setCaption(caption + downloadSectionText);
        // CAPTION SECTION - END

        Message message = bot.execute(sendPhoto);

        // DOWNLOAD SECTION - BEGIN
        if (movie.getType().equals(Type.TV)) {
            return;
        }

        DownloadRequest request = DownloadRequest.of(user.getId(), movie.getId(), movie.getImdbId());
        Optional<Download> downloadOptional = downloadClient.download(request);

        EditMessageCaption editMessage = new EditMessageCaption();
        editMessage.setChatId(user.getTelegramId());
        editMessage.setMessageId(message.getMessageId());
        editMessage.setParseMode(ParseMode.HTML);

        if (downloadOptional.isPresent()) {
            Download download = downloadOptional.get();

            StringBuilder dlBuilder = new StringBuilder();
            dlBuilder.append("\n\n<a href='")
                    .append(download.getUrl())
                    .append("'>\uD83D\uDCE5</a><b>")
                    .append("Download links are available.</b>");

            for (Link link : download.getLinks()) {
                dlBuilder.append("\n")
                        .append(link.getQuality())
                        .append(" • ")
                        .append(link.getSize())
                        .append(" • ")
                        .append("<a href='")
                        .append(link.getUrl())
                        .append("'>Download</a>");
            }
            editMessage.setCaption(caption + dlBuilder);
        } else {
            editMessage.setCaption(caption + "\n\n<b>⚠️ No download links were found!</b>");
        }
        // DOWNLOAD SECTION - END

        bot.execute(editMessage);
    }
}
