package dev.aminnorouzi.telegrambot.database;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.Map;

public class TempInMemoryMessageDatabase {

    public static final Map<Integer, Message> messages = new HashMap<>();

    public static void add(Integer id, Message message) {
        messages.putIfAbsent(id, message);
    }

    public static Message find(Integer id) {
        return messages.get(id);
    }

    public static void remove(Integer id) {
        messages.remove(id);
    }
}
