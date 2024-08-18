package com.evolutionaryeyes.beercatalogservice.controller;

import com.evolutionaryeyes.beercatalogservice.dto.BeerItemDTO;
import com.evolutionaryeyes.beercatalogservice.service.BeerItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/beer")
public class BeerItemController {

    private final BeerItemService beerItemService;

    @GetMapping()
    public ResponseEntity<Page<BeerItemDTO>> getAllBeerItems(@RequestParam Optional<Integer> page,
                                                             @RequestParam Optional<Integer> size,
                                                             @RequestParam Optional<String> sortBy,
                                                             @RequestParam Optional<Sort.Direction> sortDirection
    )
    {
        return ResponseEntity.status(HttpStatus.OK).body(beerItemService.getAllBeerItems(page, size, sortBy, sortDirection));
    }

    @GetMapping("search")
    public ResponseEntity<Page<BeerItemDTO>> getgetBeerItemsWithSearch(@RequestParam Optional<Integer> page,
                                                                       @RequestParam Optional<Integer> size,
                                                                       @RequestParam Optional<String> sortBy,
                                                                       @RequestParam Optional<Sort.Direction> sortDirection,
                                                                       @RequestParam Optional<String> query
    )
    {
        return ResponseEntity.status(HttpStatus.OK).body(beerItemService.getBeerItemsWithSearch(page, size, sortBy, sortDirection, query));
    }

    @GetMapping("beer-by-id/{id}")
    public ResponseEntity<BeerItemDTO> getBeerById(@PathVariable int id) throws Exception
    {
        return ResponseEntity.status(HttpStatus.OK).body(beerItemService.getBeerById(id));
    }

    @GetMapping("beers-by-ids")
    public ResponseEntity<Page<BeerItemDTO>> getBeersByIds(@RequestParam List<Integer> ids, @RequestParam Optional<Integer> page,
                                                           @RequestParam Optional<Integer> size,
                                                           @RequestParam Optional<String> sortBy,
                                                           @RequestParam Optional<Sort.Direction> sortDirection) throws Exception
    {
        return ResponseEntity.status(HttpStatus.OK).body(beerItemService.getBeersByIds(ids, page, size, sortBy, sortDirection));
    }

    @PostMapping("save-a-beer")
    public ResponseEntity<BeerItemDTO> saveNewBeerItem(@RequestBody BeerItemDTO beerItemDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(beerItemService.saveNewBeerItem(beerItemDTO));
    }

    @PostMapping("save-multiple-beers")
    public ResponseEntity<List<BeerItemDTO>> saveNewBeerItems(@RequestBody List<BeerItemDTO> beerItemDTOs)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(beerItemService.saveNewBeerItems(beerItemDTOs));
    }

    @PutMapping("update-beer-item/{id}")
    public ResponseEntity<BeerItemDTO> updateBeerItem(@PathVariable int id, @RequestBody BeerItemDTO beerItemDTO
    ) throws Exception
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(beerItemService.updateBeerItem(id, beerItemDTO));
    }

    @DeleteMapping("delete-beer/{id}")
    public ResponseEntity<BeerItemDTO> deleteBeerItem(@PathVariable int id) throws Exception
    {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(beerItemService.deleteBeerItem(id));
    }

    @PostMapping("add-beer-to-user-list")
    public ResponseEntity<BeerItemDTO> addBeerItemToUserList(@RequestParam int beerItemId,
                                                             @RequestParam int userId,
                                                             @RequestParam Integer rating
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(beerItemService.addBeerItemToUserList(beerItemId, userId, rating));
    }
}
