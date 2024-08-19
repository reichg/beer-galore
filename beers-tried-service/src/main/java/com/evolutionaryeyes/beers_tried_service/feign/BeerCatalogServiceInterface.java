package com.evolutionaryeyes.beers_tried_service.feign;

import com.evolutionaryeyes.beers_tried_service.dto.BeerItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("BEER-CATALOG-SERVICE")
public interface BeerCatalogServiceInterface {
    @GetMapping("api/beer/beers-by-ids")
    public ResponseEntity<Page<BeerItemDTO>> getBeersByIds(@RequestParam List<Integer> ids, PageRequest pageRequest) throws Exception;
}
