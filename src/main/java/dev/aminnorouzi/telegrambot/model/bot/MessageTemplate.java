package dev.aminnorouzi.telegrambot.model.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageTemplate {

    HOME("<b>Hi %s, I'm Sasan known as the bot. </b>\uD83D\uDC4B\uD83C\uDFFB \n\nYou wanna download a movie/tv? Just say its name and consider it as a done task. Now I'm all ears..."),
    SEARCH("<b>Your search results:</b> \n\n%s<b>Please hit a command of a movie/tv to download it.</b>"),
    SEARCH_MOVIES("%s. %s %s (%s)\n\n"),
    DOWNLOAD("%s\n\n<b>%s %s</b>\n%s - %s\n\n%s\n\n<b>%s</b>"),
    DOWNLOAD_INFO("<a href='%s'>\uD83D\uDCE5</a> <b>%s</b>"),
    DOWNLOAD_LINKS("\n%s • %s • <a href='%s'>Download</a>");

    private final String value;
}
