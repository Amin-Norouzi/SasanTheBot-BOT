package dev.aminnorouzi.telegrambot.config;

import dev.aminnorouzi.telegrambot.handler.*;
import dev.aminnorouzi.telegrambot.handler.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class BotConfig {

    private final HomeHandler homeHandler;
    private final AccountHandler accountHandler;
    private final ContactHandler contactHandler;
    private final SearchHandler searchHandler;
    private final DownloadHandler downloadHandler;

    @Value("${telegram.bot.token}")
    private String token;

    @Bean
    public String botToken() {
        return token;
    }

    @Bean
    public Set<Handler> handlers() {
        return Set.of(
                homeHandler,
                accountHandler,
                contactHandler,
                searchHandler,
                downloadHandler
        );
    }
}
