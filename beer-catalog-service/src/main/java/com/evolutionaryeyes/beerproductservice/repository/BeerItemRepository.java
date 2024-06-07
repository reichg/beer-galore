package com.evolutionaryeyes.beerproductservice.repository;

import com.evolutionaryeyes.beerproductservice.entity.BeerItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeerItemRepository extends JpaRepository<BeerItem, Integer> {
    @Query("SELECT b FROM BeerItem b WHERE b.name LIKE %:query% OR b.brewery LIKE %:query% OR b.description LIKE %:query%" )
    Page<BeerItem> findAllWithSearch(String query, PageRequest pageRequest);
}
