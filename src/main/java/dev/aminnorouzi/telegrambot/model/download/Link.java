package dev.aminnorouzi.telegrambot.model.download;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    private Long id;
    private Long downloadId;
    private String url;
    private String quality;
    private String size;
    private String season;
    private String episode;
}
