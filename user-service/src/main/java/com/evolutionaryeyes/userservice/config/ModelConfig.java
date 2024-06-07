package com.evolutionaryeyes.userservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

    @Bean
    ModelMapper mapper() {
        return new ModelMapper();
    }
}
