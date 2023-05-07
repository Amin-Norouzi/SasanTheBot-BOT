package dev.aminnorouzi.telegrambot.keyboard.impl;

import dev.aminnorouzi.telegrambot.keyboard.Keyboard;
import dev.aminnorouzi.telegrambot.keyboard.KeyboardButton;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;

@Component
public class AccountKeyboard implements Keyboard {

    @Override
    public ReplyKeyboard get(Object data) {
        return InlineKeyboardMarkup.builder()
                .keyboard(build(
                        true,
                        generate(buttons().get(0)),
                        generate(buttons().get(1)),
                        generate(buttons().get(2))
                ))
                .build();
    }

    @Override
    public List<KeyboardButton> buttons() {
        return List.of(
                KeyboardButton.MOVIES,
                KeyboardButton.DOWNLOADS,
                KeyboardButton.CONNECT
        );
    }
}
