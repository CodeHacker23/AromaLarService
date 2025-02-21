package ru.project.aromalarservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;
import ru.project.aromalarservice.model.dto.Basket;

import java.util.Base64;

@Configuration
public class SpringConfig {

    @Bean
    public Basket basket (){
        return new Basket();
    }
}
