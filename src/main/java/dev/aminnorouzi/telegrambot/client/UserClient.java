package dev.aminnorouzi.telegrambot.client;

import org.springframework.cloud.openfeign.FeignClient;

// TODO port this
@FeignClient(name = "user-service", url="https://localhost:")
public interface UserClient {
}
