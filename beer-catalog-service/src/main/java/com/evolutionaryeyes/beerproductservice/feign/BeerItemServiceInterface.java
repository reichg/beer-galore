package com.evolutionaryeyes.beerproductservice.feign;

import com.evolutionaryeyes.beerproductservice.dto.BeerTriedDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("BEERS-TRIED-SERVICE")
public interface BeerItemServiceInterface {
    @PostMapping("api/tried-beer/add")
    public ResponseEntity<BeerTriedDTO> createBeerTriedEvent(@RequestBody BeerTriedDTO beerTriedDTO);
}
