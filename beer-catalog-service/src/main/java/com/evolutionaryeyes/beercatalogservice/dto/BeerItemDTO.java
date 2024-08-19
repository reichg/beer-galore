package com.evolutionaryeyes.beercatalogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerItemDTO {
    private int beerItemId;
    private String name;
    private String description;
    private String brewery;
    private Double abv;
    private int ibu;
    private String type;
}
