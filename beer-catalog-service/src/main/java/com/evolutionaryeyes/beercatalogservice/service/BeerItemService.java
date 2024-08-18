package com.evolutionaryeyes.beercatalogservice.service;

import com.evolutionaryeyes.beercatalogservice.dto.BeerItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface BeerItemService {

    Page<BeerItemDTO> getAllBeerItems(Optional<Integer> page,
                                                      Optional<Integer> size,
                                                      Optional<String> sortBy,
                                                      Optional<Sort.Direction> sortDirection
    );

    BeerItemDTO saveNewBeerItem(BeerItemDTO beerItemDTO);

    List<BeerItemDTO> saveNewBeerItems(List<BeerItemDTO> beerItemDTOs);

    BeerItemDTO deleteBeerItem(int id) throws Exception;

    BeerItemDTO getBeerById(int id) throws Exception;

    BeerItemDTO updateBeerItem(int id, BeerItemDTO beerItemDTO
    ) throws Exception;

    BeerItemDTO addBeerItemToUserList(int beerItemId, int userId, int rating
    );

    Page<BeerItemDTO> getBeersByIds(List<Integer> ids, Optional<Integer> page,
                                    Optional<Integer> size,
                                    Optional<String> sortBy,
                                    Optional<Sort.Direction> sortDirection);

    Page<BeerItemDTO> getBeerItemsWithSearch(Optional<Integer> page,
                                                             Optional<Integer> size,
                                                             Optional<String> sortBy,
                                                             Optional<Sort.Direction> sortDirection,
                                                             Optional<String> query
    );
}
