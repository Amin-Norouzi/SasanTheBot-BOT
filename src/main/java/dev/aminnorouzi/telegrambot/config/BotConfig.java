package dev.aminnorouzi.telegrambot.config;

import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.handler.impl.AuthHandler;
import dev.aminnorouzi.telegrambot.handler.impl.DownloadHandler;
import dev.aminnorouzi.telegrambot.handler.impl.HomeHandler;
import dev.aminnorouzi.telegrambot.handler.impl.SearchHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class BotConfig {

    private final HomeHandler homeHandler;
    private final AuthHandler authHandler;
    private final DownloadHandler downloadHandler;
    private final SearchHandler searchHandler;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Bean
    public String token() {
        return botToken;
    }

    @Bean
    public String username() {
        return botUsername;
    }

    @Bean
    public Set<Handler> handlers() {
        return Set.of(
                homeHandler,
                authHandler,
                downloadHandler,
                searchHandler
        );
    }
}
