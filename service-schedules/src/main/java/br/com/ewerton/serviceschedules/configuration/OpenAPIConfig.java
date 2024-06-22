package br.com.ewerton.serviceschedules.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info = @Info(title = "Schedules Service API", version = "v1", description = "Documentation of Schedules Service"))
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components()).info(new io.swagger.v3.oas.models.info.Info().title("Schedules Service API").version("v1").license(new License().name("Ewerton Rodrigues 1.0").url("http://www.ewerton.com.br")));
    }
}
