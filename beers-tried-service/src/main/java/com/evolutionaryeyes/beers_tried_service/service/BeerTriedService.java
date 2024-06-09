package com.evolutionaryeyes.beers_tried_service.service;

import com.evolutionaryeyes.beers_tried_service.dto.BeerItemDTO;
import com.evolutionaryeyes.beers_tried_service.dto.BeerTriedDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BeerTriedService {
    BeerTriedDTO createBeerTriedEvent(BeerTriedDTO beerTriedDTO);

    BeerTriedDTO updateBeerTriedEvent(int userId, int beerItemId, BeerTriedDTO beerTriedDTO
    ) throws Exception;

    BeerTriedDTO getBeerEventByUserIdAndBeerItemId(int userId, int beerItemId
    ) throws Exception;

    List<BeerItemDTO> getTriedBeerByUserId(int userId
    ) throws Exception;

    List<BeerTriedDTO> getAllTriedBeerEvents();
}
