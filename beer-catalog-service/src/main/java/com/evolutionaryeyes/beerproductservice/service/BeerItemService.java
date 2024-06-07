package com.evolutionaryeyes.beerproductservice.service;

import com.evolutionaryeyes.beerproductservice.dto.BeerItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BeerItemService {

    ResponseEntity<Page<BeerItemDTO>> getAllBeerItems(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy, Optional<Sort.Direction> sortDirection);

    ResponseEntity<BeerItemDTO> saveNewBeerItem(BeerItemDTO beerItemDTO);

    ResponseEntity<List<BeerItemDTO>> saveNewBeerItems(List<BeerItemDTO> beerItemDTOs);

    ResponseEntity<BeerItemDTO> deleteBeerItem(int id) throws Exception;

    ResponseEntity<BeerItemDTO> getBeerById(int id) throws Exception;

    ResponseEntity<BeerItemDTO> updateBeerItem(int id, BeerItemDTO beerItemDTO) throws Exception;

    ResponseEntity<BeerItemDTO> addBeerItemToUserList(int beerItemId, int userId, int rating);

    ResponseEntity<List<BeerItemDTO>> getBeersByIds(List<Integer> ids);

    ResponseEntity<Page<BeerItemDTO>> getBeerItemsWithSearch(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy, Optional<Sort.Direction> sortDirection, Optional<String> query);
}
