package dev.aminnorouzi.telegrambot.keyboard.impl;

import dev.aminnorouzi.telegrambot.keyboard.Keyboard;
import dev.aminnorouzi.telegrambot.model.bot.Button;
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
                        generate(buttons().get(1))
                ))
                .build();
    }

    @Override
    public List<Button> buttons() {
        return List.of(
                Button.INFO,
                Button.DOWNLOADS
        );
    }
}
