package com.evolutionaryeyes.beerproductservice.service;

import com.evolutionaryeyes.beerproductservice.dto.BeerItemDTO;
import com.evolutionaryeyes.beerproductservice.repository.BeerItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BeerItemServiceImplTest {

    @Mock
    BeerItemRepository beerRepository;

    @InjectMocks
    BeerItemServiceImpl beerItemService;


    ModelMapper modelMapper;

    List<BeerItemDTO> beerItems = new ArrayList<>();


    @BeforeEach
    void setUp()
    {

        BeerItemDTO beerItem1 = BeerItemDTO.builder()
                                           .name("beer name")
                                           .type("IPA")
                                           .brewery("beer brewery")
                                           .abv(7.1)
                                           .ibu(40)
                                           .description("yummy beer description")
                                           .build();

        BeerItemDTO beerItem2 = BeerItemDTO.builder()
                                           .name("beer name 2")
                                           .type("IPA")
                                           .brewery("beer brewery 2")
                                           .abv(6.0)
                                           .ibu(40)
                                           .description("yummy beer description 2")
                                           .build();
        beerItems.add(beerItem1);
        beerItems.add(beerItem2);
    }

    @Test
    void getAllBeerItems()
    {
        var beerItems = mock(Page.class);
        when(beerRepository.findAll(any(PageRequest.class))).thenReturn(beerItems);

        beerItemService.getAllBeerItems(Optional.of(1),
                                        Optional.of(1),
                                        Optional.of("name"),
                                        Optional.of(Sort.Direction.ASC)
        );

        verify(beerRepository).findAll(any(PageRequest.class));
        verifyNoMoreInteractions(beerRepository);

    }
}