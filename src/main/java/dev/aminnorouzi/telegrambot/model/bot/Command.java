package dev.aminnorouzi.telegrambot.model.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Command {

    START("start", "/\\w{5}"),
    DOWNLOAD("download", "/dl_\\w{1}\\d+");

    private final String name;
    private final String pattern;
}
