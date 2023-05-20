package dev.aminnorouzi.telegrambot.model.bot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private Long chatId;
    private String text;
    private ReplyKeyboardMarkup keyboard;
    private String parse;
}
