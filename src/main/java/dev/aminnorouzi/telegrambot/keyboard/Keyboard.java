package dev.aminnorouzi.telegrambot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public interface Keyboard {

    ReplyKeyboard get(Object object);

    List<KeyboardButton> buttons();

    default ReplyKeyboard get() {
        return get(null);
    }

    default List<List<InlineKeyboardButton>> build(InlineKeyboardButton... buttons) {
        return build(false, buttons);
    }

    default List<List<InlineKeyboardButton>> build(boolean newRow, InlineKeyboardButton... buttons) {
        List<List<InlineKeyboardButton>> list = new ArrayList<>();

        if (newRow) {
            for (InlineKeyboardButton b : buttons) {
                list.add(List.of(b));
            }
            return list;
        } else {
            return List.of(
                    List.of(buttons)
            );
        }
    }

    default InlineKeyboardButton generate(KeyboardButton button) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(button.getText());

        if (button.getCallback() != null) {
            inlineKeyboardButton.setCallbackData(button.getCallback());
        }
        if (button.getUrl() != null) {
            inlineKeyboardButton.setUrl(button.getUrl());
        }
        if (button.getQuery() != null) {
            inlineKeyboardButton.setSwitchInlineQueryCurrentChat(button.getQuery());
        }

        return inlineKeyboardButton;
    }
}


