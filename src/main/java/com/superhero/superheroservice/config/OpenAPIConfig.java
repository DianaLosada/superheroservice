package com.superhero.superheroservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("W2M Superhero API")
                        .version("1.0")
                        .description("W2M Superhero API CRUD for managing superheroes")
                        .contact(new Contact()
                                .name("Diana Losada")
                                .email("dlosada@k-lagan.com"))
                        .termsOfService("http://swagger.io/terms/")
                );
    }
}
