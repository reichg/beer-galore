package com.evolutionaryeyes.beers_tried_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerTriedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int beerItemId;
    @Column(nullable = false)
    private int userId;
    @Column(nullable = false)
    @CreationTimestamp
    private Date timeTried;
    @Column(nullable = false)
    @UpdateTimestamp
    private Date timeUpdated;
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private int rating;

}
