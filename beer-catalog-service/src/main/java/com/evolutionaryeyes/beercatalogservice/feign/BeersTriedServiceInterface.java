package com.evolutionaryeyes.beercatalogservice.feign;

import com.evolutionaryeyes.beercatalogservice.dto.BeerTriedDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("BEERS-TRIED-SERVICE")
public interface BeersTriedServiceInterface {
    @PostMapping("api/tried-beer/add")
    public ResponseEntity<BeerTriedDTO> createBeerTriedEvent(@RequestBody BeerTriedDTO beerTriedDTO);
}
