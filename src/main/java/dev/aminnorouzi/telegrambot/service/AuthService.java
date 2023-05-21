package dev.aminnorouzi.telegrambot.service;

import dev.aminnorouzi.telegrambot.client.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClient userClient;

    public boolean isAuthenticated(Long chatId) {
        return userClient.getByTelegramId(chatId).isPresent();
    }
}
