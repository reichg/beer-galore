package com.evolutionaryeyes.beers_tried_service.service;

import com.evolutionaryeyes.beers_tried_service.dto.BeerItemDTO;
import com.evolutionaryeyes.beers_tried_service.dto.BeerTriedDTO;
import com.evolutionaryeyes.beers_tried_service.feign.BeersTriedServiceInterface;
import com.evolutionaryeyes.beers_tried_service.model.BeerTriedEntity;
import com.evolutionaryeyes.beers_tried_service.repository.BeerTriedRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BeerTriedServiceImpl implements BeerTriedService {

    @Autowired
    BeerTriedRepository beerTriedRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    Environment environment;
    @Autowired
    private BeersTriedServiceInterface beersTriedServiceInterface;

    @Override
    public BeerTriedDTO createBeerTriedEvent(BeerTriedDTO beerTriedDTO)
    {
        BeerTriedEntity beerTriedEntity = mapper.map(beerTriedDTO, BeerTriedEntity.class);
        BeerTriedEntity savedEntity = beerTriedRepository.save(beerTriedEntity);
        BeerTriedDTO savedBeerDTO = mapper.map(savedEntity, BeerTriedDTO.class);
        log.info("Saved beer: " + savedBeerDTO);
        return savedBeerDTO;
    }

    @Override
    public BeerTriedDTO updateBeerTriedEvent(int userId, int beerItemId, BeerTriedDTO beerTriedDTO
    ) throws Exception
    {
        Optional<BeerTriedEntity> existingBeerTriedEntity = beerTriedRepository.findByUserIdAndBeerItemId(userId,
                                                                                                          beerItemId
        );

        if (existingBeerTriedEntity.isPresent())
        {
            int existingBeerRating = existingBeerTriedEntity.get().getRating();
            existingBeerTriedEntity.get().setRating(beerTriedDTO.getRating());
            BeerTriedEntity updatedBeerTriedEntity = beerTriedRepository.save(existingBeerTriedEntity.get());
            BeerTriedDTO updatedBeerTriedDTO = mapper.map(updatedBeerTriedEntity, BeerTriedDTO.class);
            log.info("Updated beer rating from : " + existingBeerRating + " to: " + updatedBeerTriedDTO.getRating());
            return updatedBeerTriedDTO;
        }
        throw new Exception("cannot update beer tried event with userId: " + userId + " and beerId: " + beerItemId);
    }

    @Override
    public BeerTriedDTO getBeerEventByUserIdAndBeerItemId(int userId, int beerItemId
    ) throws Exception
    {
        Optional<BeerTriedEntity> beerTriedEntity = beerTriedRepository.findByUserIdAndBeerItemId(userId, beerItemId);
        if (beerTriedEntity.isPresent())
        {
            log.info("Retrieved beer: " + beerTriedEntity.get().toString());
            return mapper.map(beerTriedEntity.get(), BeerTriedDTO.class);
        }
        throw new Exception("beer event with userId: " + userId + " and beerId: " + beerItemId + " is not found");
    }

    @Override
    public List<BeerItemDTO> getTriedBeerByUserId(int userId
    ) throws Exception
    {
        log.info("getting from service port: " + environment.getProperty("local.server.port"));
        List<Integer> beerTriedEntityIds = beerTriedRepository.findAllByUserId(userId);
        if (!beerTriedEntityIds.isEmpty())
        {
            return beersTriedServiceInterface.getBeersByIds(beerTriedEntityIds).getBody();
        }
        return null;
    }

    @Override
    public List<BeerTriedDTO> getAllTriedBeerEvents()
    {
        List<BeerTriedEntity> allEvents = beerTriedRepository.findAll();
        return allEvents.stream().map(event -> mapper.map(event, BeerTriedDTO.class)).toList();
    }
}
