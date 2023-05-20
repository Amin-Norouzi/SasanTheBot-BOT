package dev.aminnorouzi.telegrambot.client;

import dev.aminnorouzi.telegrambot.model.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;

    @Value("${sasan.api.user-client.get-by-telegram-id}")
    private String getByTelegramIdUrl;

    public Optional<User> getByTelegramId(Long telegramId) {
        try {
            ResponseEntity<User> response = restTemplate
                    .getForEntity(getByTelegramIdUrl.formatted(telegramId), User.class);

            return Optional.ofNullable(response.getBody());
        } catch (RuntimeException exception) {
            log.error(exception.getMessage());
        }

        return Optional.empty();
    }
}
