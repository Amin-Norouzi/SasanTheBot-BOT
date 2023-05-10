package dev.aminnorouzi.telegrambot.model.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Command {

    START("start", new String[]{"/\\w{5}"}),
    DOWNLOAD("download", new String[]{"/dl_\\d+", "/\\w{5}\\s\\d+"});

    private final String name;
    private final String[] patterns;
}
