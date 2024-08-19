package com.evolutionaryeyes.beercatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BeerCatalogServiceApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(BeerCatalogServiceApplication.class, args);
    }
}
