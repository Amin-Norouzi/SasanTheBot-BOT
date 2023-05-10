package dev.aminnorouzi.telegrambot.model.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Callback {

    ACCOUNT("account-callback", "account-callback"),
    ABOUT("about-callback", "about-callback"),
    INFO("info-callback", "info-callback"),
    LIBRARY("library-callback#%s", "\\w+#\\d+"),
    CONNECT("connect-callback", "connect-callback");

    private final String value;
    private final String pattern;
}
