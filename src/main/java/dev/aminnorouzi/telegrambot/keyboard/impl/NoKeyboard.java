package dev.aminnorouzi.telegrambot.keyboard.impl;

import dev.aminnorouzi.telegrambot.keyboard.Keyboard;
import dev.aminnorouzi.telegrambot.keyboard.KeyboardButton;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;

@Component
public class NoKeyboard implements Keyboard {

    @Override
    public ReplyKeyboard get(Object object) {
        return null;
    }

    @Override
    public List<KeyboardButton> buttons() {
        return null;
    }
}
