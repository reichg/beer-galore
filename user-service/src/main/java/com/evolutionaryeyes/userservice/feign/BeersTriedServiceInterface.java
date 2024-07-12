package com.evolutionaryeyes.userservice.feign;

import com.evolutionaryeyes.userservice.dto.BeerItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("BEERS-TRIED-SERVICE")
public interface BeersTriedServiceInterface {
    @GetMapping("api/tried-beer/get-tried-beers-by-user-id")
    public ResponseEntity<List<BeerItemDTO>> getBeersTriedByUserId(@RequestParam int userId
    ) throws Exception;
}
