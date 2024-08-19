package com.evolutionaryeyes.beercatalogservice.service;

import com.evolutionaryeyes.beercatalogservice.dto.BeerItemDTO;
import com.evolutionaryeyes.beercatalogservice.dto.BeerTriedDTO;
import com.evolutionaryeyes.beercatalogservice.entity.BeerItem;
import com.evolutionaryeyes.beercatalogservice.feign.BeersTriedServiceInterface;
import com.evolutionaryeyes.beercatalogservice.repository.BeerItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BeerItemServiceImpl implements BeerItemService {

    @Autowired
    BeerItemRepository beerItemRepository;
    @Autowired
    ModelMapper mapper;

    @Autowired
    BeersTriedServiceInterface beersTriedServiceInterface;

    @Override
    public Page<BeerItemDTO> getAllBeerItems(Optional<Integer> page,
                                             Optional<Integer> size,
                                             Optional<String> sortBy,
                                             Optional<Sort.Direction> sortDirection
    )
    {
        PageRequest pageRequest = PageRequest.of(page.orElse(0),
                                                 size.orElse(10),
                                                 sortDirection.orElse(Sort.Direction.ASC),
                                                 sortBy.orElse("name")
        );
        Page<BeerItem> beerItems = beerItemRepository.findAll(pageRequest);
        log.info("Fetched all beer items: " + "\n" + beerItems);
        return beerItems.map(beerItem -> mapper.map(beerItem, BeerItemDTO.class));
    }

    @Override
    public Page<BeerItemDTO> getBeerItemsWithSearch(Optional<Integer> page,
                                                    Optional<Integer> size,
                                                    Optional<String> sortBy,
                                                    Optional<Sort.Direction> sortDirection,
                                                    Optional<String> query
    )
    {
        PageRequest pageRequest = PageRequest.of(page.orElse(0),
                                                 size.orElse(10),
                                                 sortDirection.orElse(Sort.Direction.ASC),
                                                 sortBy.orElse("name")
        );
        Page<BeerItem> beerItems = beerItemRepository.findAllWithSearch(query.orElse(""), pageRequest);
        return beerItems.map(beerItem -> mapper.map(beerItem, BeerItemDTO.class));
    }

    @Override
    public BeerItemDTO saveNewBeerItem(BeerItemDTO beerItemDTO)
    {
        BeerItem beerItem = mapper.map(beerItemDTO, BeerItem.class);
        BeerItem savedBeerItem = beerItemRepository.save(beerItem);
        BeerItemDTO savedBeerItemDTO = mapper.map(savedBeerItem, BeerItemDTO.class);
        log.info("Saved new beer item: " + savedBeerItemDTO);
        return savedBeerItemDTO;
    }

    @Override
    public List<BeerItemDTO> saveNewBeerItems(List<BeerItemDTO> beerItemDTOs)
    {
        List<BeerItem> beerItems = beerItemDTOs.stream()
                                               .map(beerItemDTO -> mapper.map(beerItemDTO, BeerItem.class))
                                               .toList();
        List<BeerItem> savedBeerItems = beerItemRepository.saveAll(beerItems);
        List<BeerItemDTO> savedBeerItemDTOs = savedBeerItems.stream()
                                                            .map(beerItem -> mapper.map(beerItem, BeerItemDTO.class))
                                                            .toList();
        log.info("Saved new beer items: " + savedBeerItemDTOs);
        return savedBeerItemDTOs;
    }

    @Override
    public BeerItemDTO getBeerById(int id) throws Exception
    {
        Optional<BeerItem> beerItem = beerItemRepository.findById(id);
        if (beerItem.isPresent())
        {
            log.info("Found beer item: " + beerItem);
            return mapper.map(beerItem.get(), BeerItemDTO.class);
        }
        throw new Exception("BeerItem with id: " + id + " does not exist.");
    }

    @Override
    public BeerItemDTO updateBeerItem(int id, BeerItemDTO beerItemDTO
    ) throws Exception
    {
        Optional<BeerItem> beerItem = beerItemRepository.findById(id);
        if (beerItem.isPresent())
        {
            beerItem.get().fromDto(beerItemDTO);
            BeerItem updatedBeerItem = beerItemRepository.save(beerItem.get());
            return mapper.map(updatedBeerItem, BeerItemDTO.class);
        } else
        {
            throw new Exception("Beer item with id: " + id + " does not exist.");
        }
    }

    @Override
    public BeerItemDTO addBeerItemToUserList(int beerItemId, int userId, int rating
    )
    {
        Optional<BeerItem> beerItem = beerItemRepository.findById(beerItemId);
        if (beerItem.isPresent())
        {
            BeerTriedDTO beerTriedDTO = BeerTriedDTO.builder()
                                                    .beerItemId(beerItemId)
                                                    .userId(userId)
                                                    .rating(rating)
                                                    .build();
            BeerItemDTO userSavedBeerItemDTO = mapper.map(beerItem.get(), BeerItemDTO.class);
            BeerTriedDTO savedBeerTriedDTO = beersTriedServiceInterface.createBeerTriedEvent(beerTriedDTO).getBody();
            log.info("added beer item with id: " + savedBeerTriedDTO.getBeerItemId());
            log.info("BeerTried: " + savedBeerTriedDTO);
            return userSavedBeerItemDTO;
        }
        return null;
    }

    @Override
    public Page<BeerItemDTO> getBeersByIds(List<Integer> ids, Optional<Integer> page,
                                           Optional<Integer> size,
                                           Optional<String> sortBy,
                                           Optional<Sort.Direction> sortDirection)
    {
        PageRequest pageRequest = PageRequest.of(page.orElse(0),
                                                 size.orElse(10),
                                                 sortDirection.orElse(Sort.Direction.ASC),
                                                 sortBy.orElse("name")
        );
        Page<BeerItem> beerItems = beerItemRepository.findAllByIds(ids, pageRequest);
//        List<BeerItemDTO> beerItemDTOs = beerItems.stream()
//                                                  .map(beerItem -> mapper.map(beerItem, BeerItemDTO.class))
//                                                  .toList();
//        log.info("found beer items: " + beerItemDTOs);
        return beerItems.map(beerItem -> mapper.map(beerItem, BeerItemDTO.class));
    }


    @Override
    public BeerItemDTO deleteBeerItem(int id) throws Exception
    {
        Optional<BeerItem> beerItem = beerItemRepository.findById(id);
        if (beerItem.isPresent())
        {
            BeerItemDTO deletedBeerItemDTO = mapper.map(beerItem.get(), BeerItemDTO.class);
            beerItemRepository.delete(beerItem.get());
            log.info("Deleting beer: " + deletedBeerItemDTO);
            return deletedBeerItemDTO;
        }
        throw new Exception("BeerItem with id: " + id + " does not exist.");
    }
}
