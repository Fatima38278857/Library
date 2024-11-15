package com.example.Library.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Library API", version = "1.0", description = "API для работы с библиотекой"),
        servers = @Server(url = "http://localhost:8080", description = "Локальный сервер")
)
public class SwaggerConfig {
}
