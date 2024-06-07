package com.evolutionaryeyes.beers_tried_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerItemDTO {
    private String name;
    private String description;
    private String brewery;
    private Double abv;
    private Double ibu;
    private String type;


}
