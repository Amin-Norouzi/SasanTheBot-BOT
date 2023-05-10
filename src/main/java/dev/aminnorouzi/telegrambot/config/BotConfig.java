package dev.aminnorouzi.telegrambot.config;

import dev.aminnorouzi.telegrambot.handler.Handler;
import dev.aminnorouzi.telegrambot.handler.impl.CallbackHandler;
import dev.aminnorouzi.telegrambot.handler.impl.InlineQueryHandler;
import dev.aminnorouzi.telegrambot.handler.impl.MessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class BotConfig {

    private final CallbackHandler callbackHandler;
    private final MessageHandler messageHandler;
    private final InlineQueryHandler inlineQueryHandler;

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
                callbackHandler,
                messageHandler,
                inlineQueryHandler
        );
    }
}
