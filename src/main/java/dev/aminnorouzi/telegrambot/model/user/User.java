package dev.aminnorouzi.telegrambot.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private Long telegramId;
    private Long telegramChatId;
    private String fullName;
    private String email;
    private Status status;
    private LocalDateTime createdAt;
}
