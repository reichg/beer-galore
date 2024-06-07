package com.evolutionaryeyes.beerproductservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BeerProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerProductServiceApplication.class, args);
	}

}
