package com.evolutionaryeyes.beers_tried_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    ModelMapper mapper()
    {
        return new ModelMapper();
    }
}
