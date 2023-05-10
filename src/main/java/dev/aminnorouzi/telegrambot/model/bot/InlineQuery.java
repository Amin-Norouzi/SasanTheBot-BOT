package dev.aminnorouzi.telegrambot.model.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InlineQuery {

    MOVIES("movies"),
    DOWNLOADS("downloads");

    private final String query;
}
