package com.evolutionaryeyes.userservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BeerTriedDTO {
    private int id;
    private int beerItemId;
    private int userId;
    private Date timeTried;
    private Date timeUpdated;
    private int rating;
}
