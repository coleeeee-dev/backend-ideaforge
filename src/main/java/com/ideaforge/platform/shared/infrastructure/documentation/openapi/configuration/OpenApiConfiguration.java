package com.ideaforge.platform.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI ideaforgeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("IdeaForge Platform API")
                        .description("Backend API for IdeaForge platform")
                        .version("v1.0.0"))
                .servers(List.of(
                        new Server()
                                .url("/")
                                .description("Current server"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local development server"),
                        new Server()
                                .url("https://backend-ideaforge.onrender.com")
                                .description("Render production server")
                ));
    }
}