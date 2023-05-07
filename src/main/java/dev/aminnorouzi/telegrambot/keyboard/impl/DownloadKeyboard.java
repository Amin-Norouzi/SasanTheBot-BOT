package dev.aminnorouzi.telegrambot.keyboard.impl;

import dev.aminnorouzi.telegrambot.keyboard.Keyboard;
import dev.aminnorouzi.telegrambot.keyboard.KeyboardButton;
import dev.aminnorouzi.telegrambot.model.movie.Movie;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class DownloadKeyboard implements Keyboard {

    @Override
    public ReplyKeyboard get(Object data) {
        Movie movie = (Movie) data;

        InlineKeyboardButton button = generate(buttons().get(0));
        button.setCallbackData(button.getCallbackData().formatted(movie.getTmdbId()));

        return InlineKeyboardMarkup.builder()
                .keyboard(build(
                        button
                ))
                .build();
    }

    @Override
    public List<KeyboardButton> buttons() {
        return List.of(
                KeyboardButton.LIBRARY
        );
    }
}
