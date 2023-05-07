package dev.aminnorouzi.telegrambot.database;

import dev.aminnorouzi.telegrambot.model.Stage;
import dev.aminnorouzi.telegrambot.model.user.User;

import java.util.HashMap;
import java.util.Map;

public class TempInMemoryUserDatabase {

    public static final Map<Long, User> users = new HashMap<>();

    public static void add(Long id, User user) {
        users.putIfAbsent(id, user);
    }

    public static User find(Long id) {
        return users.get(id);
    }

    public static boolean exists(Long id) {
        return users.containsKey(id);
    }

    public static void remove(Long id) {
        users.remove(id);
    }

    public static void update(Long id, User user) {
        User found = find(id);
        if (found != null) {
            found.setId(user.getId());
            found.setChatId(user.getChatId());
            found.setUsername(user.getUsername());
            found.setFirstName(user.getFirstName());
            found.setLastName(user.getLastName());
            found.setStage(user.getStage());
            found.setMovies(user.getMovies());
            found.setDownloads(user.getDownloads());
        }
    }

    public static void changeState(Long id, Stage stage) {
        User user = find(id);
        if (user != null) {
            user.setStage(stage);
        }
    }

//    private static void addMovieToLibrary(Long id, Stage stage) {
//        User user = find(id);
//        if (user != null) {
//            user.setStage(user.getStage());
//        }
//    }
//
//    private static void addMovieToDownloads(Long id, Stage stage) {
//        User user = find(id);
//        if (user != null) {
//            user.setStage(user.getStage());
//        }
//    }
}
