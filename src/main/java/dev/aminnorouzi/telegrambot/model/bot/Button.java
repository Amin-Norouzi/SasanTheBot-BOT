package dev.aminnorouzi.telegrambot.model.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Button {

    ABOUT("About", Callback.ABOUT.getValue(), null, null),
    ACCOUNT("Account", Callback.ACCOUNT.getValue(), null, null),
    INFO("Info", Callback.INFO.getValue(), null, null),
    DOWNLOADS("Downloads", null, InlineQuery.DOWNLOADS.getQuery(), null),
    CONTACT("Support", null, null, "https://sasanthebot.com/contact");

    private final String text;
    private final String callback;
    private final String query;
    private final String url;
}
