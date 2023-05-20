package dev.aminnorouzi.telegrambot.client;

import dev.aminnorouzi.telegrambot.model.download.Download;
import dev.aminnorouzi.telegrambot.model.download.DownloadRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DownloadClient {

    private final RestTemplate restTemplate;

    @Value("${sasan.api.download-client.download}")
    private String downloadUrl;

    public Optional<Download> download(@RequestBody DownloadRequest request) {
        try {
            ResponseEntity<Download> response = restTemplate
                    .postForEntity(downloadUrl, request, Download.class);

            return Optional.ofNullable(response.getBody());
        } catch (RuntimeException exception) {
            log.error(exception.getMessage());
        }

        return Optional.empty();
    }
}

