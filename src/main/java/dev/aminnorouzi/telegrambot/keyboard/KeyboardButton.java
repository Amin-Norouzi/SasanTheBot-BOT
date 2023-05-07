package dev.aminnorouzi.telegrambot.keyboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KeyboardButton {

    AUTH("Login with SasanTheBot", null, null, "https://sasanthebot.com/login"),
    ACCOUNT("My Account", "account-callback", null, null),
    MOVIES("Movies", null, "movies", null),
    DOWNLOADS("Downloads", null, "downloads", null),
    CONTACT("Contact Admin", null, null, "https://sasanthebot.com/contact"),
    LIBRARY("Add to Library", "library-callback#%s", null, null),
    CONNECT("Connect to MovieStalker \uD83C\uDF7F", "connect-callback", null, null);

    private final String text;
    private final String callback;
    private final String query;
    private final String url;
}
