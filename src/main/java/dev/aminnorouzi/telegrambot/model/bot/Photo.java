package dev.aminnorouzi.telegrambot.model.bot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo {

    private Long chatId;
    private InputFile file;
    private String caption;
    private ReplyKeyboard keyboard;
    private String parse;
}
