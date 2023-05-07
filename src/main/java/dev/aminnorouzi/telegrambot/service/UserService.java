package dev.aminnorouzi.telegrambot.service;

import dev.aminnorouzi.telegrambot.database.TempInMemoryUserDatabase;
import dev.aminnorouzi.telegrambot.model.Stage;
import dev.aminnorouzi.telegrambot.model.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    public User findOrSaveIfNotExists(Update update) {
        Long id = extractId(update);
        if (exists(id)) {
            return getById(id);
        }

        return save(update);
    }

    private boolean exists(Long id) {
        return TempInMemoryUserDatabase.exists(id);
    }

    private User save(Update update) {
        Message message = update.getMessage();

        User user = User.builder()
                .id(message.getFrom().getId())
                .chatId(message.getChatId().toString())
                .username(message.getFrom().getUserName())
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .stage(Stage.HOME)
                .movies(new ArrayList<>())
                .downloads(new ArrayList<>())
                .joinedIn(LocalDateTime.now())
                .build();

        TempInMemoryUserDatabase.add(message.getFrom().getId(), user);

        log.info("Saved new user: {}", user);
        return user;
    }

    public User getById(Long id) {
        return TempInMemoryUserDatabase.find(id);
    }

    private Long extractId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }

        return null;
    }

    public void changeStage(Long id, Stage stage) {
        TempInMemoryUserDatabase.changeState(id , stage);
    }
}
