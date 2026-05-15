package com.ideaforge.platform.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI ideaforgeOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("IdeaForge Platform API")
                        .description("Backend API for IdeaForge, following the Sendify modular DDD style")
                        .version("v1")
                        .contact(new Contact().name("IdeaForge Team")))
                .servers(List.of(new Server().url("http://localhost:8080").description("Local server")));
    }
}
