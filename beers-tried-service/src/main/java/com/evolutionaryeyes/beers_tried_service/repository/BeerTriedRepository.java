package com.evolutionaryeyes.beers_tried_service.repository;

import com.evolutionaryeyes.beers_tried_service.model.BeerTriedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeerTriedRepository extends JpaRepository<BeerTriedEntity, Integer> {
    Optional<BeerTriedEntity> findByUserIdAndBeerItemId(int userId, int beerItemId);

    @Query("SELECT beerItemId FROM BeerTriedEntity bte WHERE bte.userId = ?1")
    List<Integer> findAllByUserId(int userId);
}
