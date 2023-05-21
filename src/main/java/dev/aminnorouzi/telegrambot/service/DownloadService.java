package dev.aminnorouzi.telegrambot.service;

import dev.aminnorouzi.telegrambot.client.DownloadClient;
import dev.aminnorouzi.telegrambot.client.MovieClient;
import dev.aminnorouzi.telegrambot.client.UserClient;
import dev.aminnorouzi.telegrambot.model.download.Download;
import dev.aminnorouzi.telegrambot.model.download.DownloadRequest;
import dev.aminnorouzi.telegrambot.model.movie.Movie;
import dev.aminnorouzi.telegrambot.model.movie.Type;
import dev.aminnorouzi.telegrambot.model.user.User;
import dev.aminnorouzi.telegrambot.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DownloadService {

    private final DownloadClient downloadClient;
    private final MovieClient movieClient;
    private final UserClient userClient;
    private final StringUtil stringUtil;

    public SendPhoto inform(String command, Long chatId) {
        String type = stringUtil.extractMovieType(command);
        Long tmdbId = stringUtil.extractMovieTmdbId(command);

        Movie movie = movieClient.getByTmdbId(tmdbId, type)
                .orElseThrow();

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setParseMode(ParseMode.HTML);
        sendPhoto.setPhoto(new InputFile(movie.getPoster()));

        String info;
        if (movie.getType().equals(Type.MOVIE)) {
            info = "\uD83D\uDD04 Searching for download links...";
        } else {
            info = "⚠️ TV shows are not available for now.";
        }
        String caption = stringUtil.generateMovieCaption(movie, info);

        sendPhoto.setCaption(caption);
        return sendPhoto;
    }

    public boolean supports(String command) {
        String type = stringUtil.extractMovieType(command);
        return type.equals("movie");
    }

    public EditMessageCaption download(String command, Long chatId, Integer messageId) {
        String type = stringUtil.extractMovieType(command);
        Long tmdbId = stringUtil.extractMovieTmdbId(command);

        Movie movie = movieClient.getByTmdbId(tmdbId, type)
                .orElseThrow();
        User user = userClient.getByTelegramId(chatId)
                .orElseThrow();

        DownloadRequest request = DownloadRequest.of(user.getId(), movie.getId(), movie.getImdbId());
        Optional<Download> downloadOptional = downloadClient.download(request);

        EditMessageCaption editMessage = new EditMessageCaption();
        editMessage.setChatId(user.getTelegramId());
        editMessage.setMessageId(messageId);
        editMessage.setParseMode(ParseMode.HTML);

        if (downloadOptional.isPresent()) {
            Download download = downloadOptional.get();

            String caption = stringUtil.generateMovieCaption(movie);
            String data = stringUtil.generateMovieCaptionWithLinks(download);
            editMessage.setCaption(caption + data);
        } else {
            String caption = stringUtil.generateMovieCaption(movie, "⚠️ No download links were found!");
            editMessage.setCaption(caption);
        }

        return editMessage;
    }
}
