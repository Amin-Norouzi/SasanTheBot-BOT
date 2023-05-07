package dev.aminnorouzi.telegrambot.model.download;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Download {

    private Long id;
    private Long movieId;
    private Long providerId;
    private String url;
    private Type type;
    private List<Link> links;
    private Downloaded downloaded;
}
