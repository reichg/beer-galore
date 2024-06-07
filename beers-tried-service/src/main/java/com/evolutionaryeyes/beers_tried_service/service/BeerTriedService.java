package com.evolutionaryeyes.beers_tried_service.service;

import com.evolutionaryeyes.beers_tried_service.dto.BeerItemDTO;
import com.evolutionaryeyes.beers_tried_service.dto.BeerTriedDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BeerTriedService {
    ResponseEntity<BeerTriedDTO> createBeerTriedEvent(BeerTriedDTO beerTriedDTO);

    ResponseEntity<BeerTriedDTO> updateBeerTriedEvent(int userId, int beerItemId, BeerTriedDTO beerTriedDTO) throws Exception;

    ResponseEntity<BeerTriedDTO> getBeerEventByUserIdAndBeerItemId(int userId, int beerItemId) throws Exception;

    ResponseEntity<List<BeerItemDTO>> getTriedBeerByUserId(
            int userId
    ) throws Exception;

    ResponseEntity<List<BeerTriedDTO>> getAllTriedBeerEvents();
}
