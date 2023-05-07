package dev.aminnorouzi.telegrambot.handler.impl;

import dev.aminnorouzi.telegrambot.core.Bot;
import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.model.Stage;
import dev.aminnorouzi.telegrambot.model.user.User;
import dev.aminnorouzi.telegrambot.service.ContactService;
import dev.aminnorouzi.telegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class ContactHandler implements Handler {

    private final ContactService contactService;
    private final UserService userService;

    @Override
    public boolean supports(Update update) {
        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            Long userId = update.getMessage().getFrom().getId();
            User user = userService.getById(userId);

            boolean isCommand = text.equals("/start") || text.equals("/cancel");

            return user.getStage().equals(Stage.CONTACT) && !isCommand;
        }

        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            return data.equalsIgnoreCase("contact-callback");
        }

        return false;
    }

    @SneakyThrows
    @Override
    public void handle(Update update, Bot bot) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            bot.execute(getDefaultAnswer(callbackQuery));

            Long userId = callbackQuery.getFrom().getId();
            userService.changeStage(userId, Stage.CONTACT);

            Message message = callbackQuery.getMessage();

            EditMessageText editMessage = EditMessageText.builder()
                    .chatId(message.getChatId())
                    .messageId(message.getMessageId())
                    .text("Write your message to support. Use /cancel to return to the main menu.")
                    .build();

            bot.execute(editMessage);
        }

        if (update.hasMessage()) {
            Message message = update.getMessage();

            contactService.receive(message);

            Long userId = message.getFrom().getId();
            userService.changeStage(userId, Stage.HOME);

            SendMessage sendMessage = SendMessage.builder()
                    .chatId(message.getChatId())
                    .text("Thanks for your feedback.")
                    .build();

            bot.execute(sendMessage);
        }
    }

    private AnswerCallbackQuery getDefaultAnswer(CallbackQuery callbackQuery) {
        return AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQuery.getId())
//                .text("My friends call me Amin but you can call me anytime. \uD83D\uDC85\uD83C\uDFFB")
//                .showAlert(true)
                .build();
    }
}
