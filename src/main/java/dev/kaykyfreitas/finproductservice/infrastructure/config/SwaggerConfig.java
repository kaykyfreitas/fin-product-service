package dev.kaykyfreitas.finproductservice.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fin Product Service")
                        .version("0.1")
                        .description("A service for managing products.")
                        .contact(new Contact()
                                .name("Kayky Freitas")
                                .email("kayky_defreitas@hotmail.com")
                                .url("https://github.com/kaykyfreitas")
                        )
                );
    }

}
