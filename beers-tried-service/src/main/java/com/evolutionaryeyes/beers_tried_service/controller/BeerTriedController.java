package com.evolutionaryeyes.beers_tried_service.controller;

import com.evolutionaryeyes.beers_tried_service.dto.BeerItemDTO;
import com.evolutionaryeyes.beers_tried_service.dto.BeerTriedDTO;
import com.evolutionaryeyes.beers_tried_service.service.BeerTriedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tried-beer")
@Slf4j
public class BeerTriedController {

    @Autowired
    BeerTriedService beerTriedService;

    @GetMapping()
    public ResponseEntity<List<BeerTriedDTO>> getAllTriedBeerEvents()
    {
        return ResponseEntity.status(HttpStatus.OK).body(beerTriedService.getAllTriedBeerEvents());
    }

    @PostMapping("add")
    public ResponseEntity<BeerTriedDTO> createBeerTriedEvent(@RequestBody BeerTriedDTO beerTriedDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(beerTriedService.createBeerTriedEvent(beerTriedDTO));
    }

    @PutMapping("update")
    public ResponseEntity<BeerTriedDTO> updateBeerTriedEvent(@RequestParam int beerItemId,
                                                             @RequestParam int userId,
                                                             @RequestBody BeerTriedDTO beerTriedDTO
    ) throws Exception
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(beerTriedService.updateBeerTriedEvent(userId, beerItemId, beerTriedDTO));
    }

    @GetMapping("getTriedBeersByUserId")
    public ResponseEntity<List<BeerItemDTO>> getBeersTriedByUserId(@RequestParam int userId) throws Exception
    {
        return ResponseEntity.status(HttpStatus.OK).body(beerTriedService.getTriedBeerByUserId(userId));
    }
}
