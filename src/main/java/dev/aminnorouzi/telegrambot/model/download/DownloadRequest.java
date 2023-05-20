package dev.aminnorouzi.telegrambot.model.download;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadRequest {

    private Long userId;
    private Long movieId;
    private String imdbId;

    public static DownloadRequest of(Long userId, Long movieId, String imdbId) {
        return new DownloadRequest(userId, movieId, imdbId);
    }
}