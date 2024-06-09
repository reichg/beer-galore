package com.evolutionaryeyes.beerproductservice.entity;

import com.evolutionaryeyes.beerproductservice.dto.BeerItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int beerItemId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String brewery;

    @Column(nullable = false)
    private Double abv;

    @Column(nullable = false)
    private int ibu;

    @Column(nullable = false)
    private String type;

    public BeerItem fromDto(BeerItemDTO beerItemDTO)
    {
        this.setName(beerItemDTO.getName());
        this.setDescription(beerItemDTO.getDescription());
        this.setBrewery(beerItemDTO.getBrewery());
        this.setAbv(beerItemDTO.getAbv());
        this.setIbu(beerItemDTO.getIbu());
        this.setType(beerItemDTO.getType());
        return this;
    }
}
