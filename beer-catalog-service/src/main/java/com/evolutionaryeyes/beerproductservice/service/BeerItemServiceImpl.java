package com.evolutionaryeyes.beerproductservice.service;

import com.evolutionaryeyes.beerproductservice.dto.BeerItemDTO;
import com.evolutionaryeyes.beerproductservice.dto.BeerTriedDTO;
import com.evolutionaryeyes.beerproductservice.entity.BeerItem;
import com.evolutionaryeyes.beerproductservice.feign.BeerItemServiceInterface;
import com.evolutionaryeyes.beerproductservice.repository.BeerItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BeerItemServiceImpl implements BeerItemService{

    @Autowired
    BeerItemRepository beerItemRepository;
    @Autowired
    ModelMapper mapper;

    @Autowired
    BeerItemServiceInterface beerItemServiceInterface;
    @Override
    public ResponseEntity<Page<BeerItemDTO>> getAllBeerItems(
            Optional<Integer> page,
            Optional<Integer> size,
            Optional<String> sortBy,
            Optional<Sort.Direction> sortDirection
    ) {
        PageRequest pageRequest = PageRequest.of(
                page.orElse(0),
                size.orElse(10),
                sortDirection.orElse(Sort.Direction.ASC),
                sortBy.orElse("name")
        );
        Page<BeerItem> beerItems = beerItemRepository.findAll(pageRequest);
        log.info("Fetched all beer items: " + "\n" + beerItems);
        return ResponseEntity.status(HttpStatus.OK).body(beerItems.map(beerItem -> mapper.map(beerItem, BeerItemDTO.class)));
    }

    @Override
    public ResponseEntity<Page<BeerItemDTO>> getBeerItemsWithSearch(
            Optional<Integer> page,
            Optional<Integer> size,
            Optional<String> sortBy,
            Optional<Sort.Direction> sortDirection,
            Optional<String> query
    ) {
        PageRequest pageRequest = PageRequest.of(
                page.orElse(0),
                size.orElse(10),
                sortDirection.orElse(Sort.Direction.ASC),
                sortBy.orElse("name")
        );
        Page<BeerItem> beerItems = beerItemRepository.findAllWithSearch(query.orElse(""), pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(beerItems.map(beerItem -> mapper.map(beerItem, BeerItemDTO.class)));
    }

    @Override
    public ResponseEntity<BeerItemDTO> saveNewBeerItem(BeerItemDTO beerItemDTO) {
        BeerItem beerItem = mapper.map(beerItemDTO, BeerItem.class);
        BeerItem savedBeerItem = beerItemRepository.save(beerItem);
        BeerItemDTO savedBeerItemDTO = mapper.map(savedBeerItem, BeerItemDTO.class);
        log.info("Saved new beer item: " + savedBeerItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBeerItemDTO);
    }

    @Override
    public ResponseEntity<List<BeerItemDTO>> saveNewBeerItems(List<BeerItemDTO> beerItemDTOs) {
        List<BeerItem> beerItems = beerItemDTOs.stream().map(beerItemDTO -> mapper.map(beerItemDTO, BeerItem.class)).toList();
        List<BeerItem> savedBeerItems = beerItemRepository.saveAll(beerItems);
        List<BeerItemDTO> savedBeerItemDTOs = savedBeerItems.stream().map(beerItem -> mapper.map(beerItem, BeerItemDTO.class)).toList();
        log.info("Saved new beer items: " + savedBeerItemDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBeerItemDTOs);
    }
    @Override
    public ResponseEntity<BeerItemDTO> getBeerById(int id) throws Exception {
        Optional<BeerItem> beerItem = beerItemRepository.findById(id);
        if (beerItem.isPresent()) {
            log.info("Found beer item: " + beerItem);
            return ResponseEntity.status(HttpStatus.OK).body(mapper.map(beerItem.get(), BeerItemDTO.class));
        }
        throw new Exception("BeerItem with id: " + id + " does not exist.");
    }

    @Override
    public ResponseEntity<BeerItemDTO> updateBeerItem(int id, BeerItemDTO beerItemDTO) throws Exception {
        Optional<BeerItem> beerItem = beerItemRepository.findById(id);
        if (beerItem.isPresent()) {
            beerItem.get().fromDto(beerItemDTO);
            BeerItem updatedBeerItem = beerItemRepository.save(beerItem.get());
            BeerItemDTO updatedBeerItemDTO = mapper.map(updatedBeerItem, BeerItemDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedBeerItemDTO);
        } else {
            throw new Exception("Beer item with id: " + id + " does not exist.");
        }
    }

    @Override
    public ResponseEntity<BeerItemDTO> addBeerItemToUserList(int beerItemId, int userId, int rating) {
        Optional<BeerItem> beerItem = beerItemRepository.findById(beerItemId);
        if (beerItem.isPresent()) {
            BeerTriedDTO beerTriedDTO = BeerTriedDTO.builder()
                    .beerItemId(beerItemId)
                    .userId(userId)
                    .rating(rating)
                    .build();
            BeerItemDTO userSavedBeerItemDTO = mapper.map(beerItem.get(), BeerItemDTO.class);
            BeerTriedDTO savedBeerTriedDTO = beerItemServiceInterface.createBeerTriedEvent(beerTriedDTO).getBody();
            log.info("added beer item with id: " + savedBeerTriedDTO.getBeerItemId());
            log.info("BeerTried: " + savedBeerTriedDTO);
            return ResponseEntity.status(HttpStatus.OK).body(userSavedBeerItemDTO);

        }
        return null;
    }

    @Override
    public ResponseEntity<List<BeerItemDTO>> getBeersByIds(List<Integer> ids) {
        List<BeerItem> beerItems = beerItemRepository.findAllById(ids);
        List<BeerItemDTO> beerItemDTOs = beerItems.stream().map(beerItem -> mapper.map(beerItem, BeerItemDTO.class)).toList();
        log.info("found beer items: " + beerItemDTOs);
        return ResponseEntity.status(HttpStatus.OK).body(beerItemDTOs);
    }



    @Override
    public ResponseEntity<BeerItemDTO> deleteBeerItem(int id) throws Exception {
        Optional<BeerItem> beerItem = beerItemRepository.findById(id);
        if (beerItem.isPresent()) {
            BeerItemDTO deletedBeerItemDTO = mapper.map(beerItem.get(), BeerItemDTO.class);
            beerItemRepository.delete(beerItem.get());
            log.info("Deleting beer: " + deletedBeerItemDTO);
            return ResponseEntity.status(HttpStatus.OK).body(deletedBeerItemDTO);
        }
        throw new Exception("BeerItem with id: " + id + " does not exist.");
    }
}
