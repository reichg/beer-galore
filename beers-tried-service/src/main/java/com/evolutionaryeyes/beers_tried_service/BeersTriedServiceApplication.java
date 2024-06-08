package com.evolutionaryeyes.beers_tried_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BeersTriedServiceApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(BeersTriedServiceApplication.class, args);
    }
}
