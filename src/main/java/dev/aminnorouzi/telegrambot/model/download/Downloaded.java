package dev.aminnorouzi.telegrambot.model.download;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Downloaded {

    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
}
