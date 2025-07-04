package com.nequi.franchise.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Franchise API")
                        .version("1.0")
                        .description("API para gestionar franquicias con RouterFunctions y Handlers"));
    }

    @Bean
    public GroupedOpenApi franchiseApi() {
        return GroupedOpenApi.builder()
                .group("franchise")
                .pathsToMatch("/api/franchises/**")
                .build();
    }

    @Bean
    public GroupedOpenApi branchApi() {
        return GroupedOpenApi.builder()
                .group("branch")
                .pathsToMatch("/api/branches/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("product")
                .pathsToMatch("/api/products/**")
                .build();
    }
}
