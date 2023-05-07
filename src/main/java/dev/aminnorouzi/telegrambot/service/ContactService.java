package dev.aminnorouzi.telegrambot.service;

import dev.aminnorouzi.telegrambot.database.TempInMemoryMessageDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {

    public void receive(Message message) {
        TempInMemoryMessageDatabase.add(message.getMessageId(), message);

        log.warn("Received message: \nFrom: {}\nText: {}\n", message.getFrom().getId(), message.getText());
    }
}
