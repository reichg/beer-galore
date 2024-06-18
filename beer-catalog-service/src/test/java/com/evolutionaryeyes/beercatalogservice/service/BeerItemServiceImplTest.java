package com.evolutionaryeyes.beercatalogservice.service;

import com.evolutionaryeyes.beercatalogservice.dto.BeerItemDTO;
import com.evolutionaryeyes.beercatalogservice.entity.BeerItem;
import com.evolutionaryeyes.beercatalogservice.repository.BeerItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
    @Spy
    ModelMapper modelMapper;

    @Autowired
    ModelMapper usedModelMapper;

    BeerItemDTO beerItemDTO1;
    BeerItemDTO beerItemDTO2;

    BeerItem beerItem1;
    BeerItem beerItem2;
    List<BeerItem> beerItems = new ArrayList<>();
    List<BeerItemDTO> beerItemDTOs = new ArrayList<>();


    @BeforeEach
    void setUp()
    {

        beerItemDTO1 = BeerItemDTO.builder()
                               .beerItemId(1)
                               .name("awesome beer")
                               .type("IPA")
                               .brewery("awesome brewery")
                               .abv(7.1)
                               .ibu(40)
                               .description("awesome beer description")
                               .build();

        beerItemDTO2 = BeerItemDTO.builder()
                               .beerItemId(2)
                               .name("yummy beer")
                               .type("Golden")
                               .brewery("yummy brewery")
                               .abv(6.0)
                               .ibu(40)
                               .description("this is a new beer description")
                               .build();

        beerItem1 = modelMapper.map(beerItemDTO1, BeerItem.class);
        beerItem2 = modelMapper.map(beerItemDTO2, BeerItem.class);

        beerItemDTOs.add(beerItemDTO1);
        beerItemDTOs.add(beerItemDTO2);

        beerItems.add(usedModelMapper.map(beerItemDTO1, BeerItem.class));
        beerItems.add(usedModelMapper.map(beerItemDTO2, BeerItem.class));
    }

    @Test
    void getAllBeerItems()
    {
        Page<BeerItem> beerItemsPage = new PageImpl<>(beerItems);
        when(beerRepository.findAll(any(PageRequest.class))).thenReturn(beerItemsPage);
        Page<BeerItemDTO> beerItemsDTOPage = beerItemService.getAllBeerItems(Optional.ofNullable(null),
                                                                             Optional.ofNullable(null),
                                                                             Optional.ofNullable(null),
                                                                             Optional.ofNullable(null)
        );
        verify(beerRepository).findAll(any(PageRequest.class));
        verifyNoMoreInteractions(beerRepository);

        Assertions.assertThat(beerItemsDTOPage.getSize()).isEqualTo(2);

    }

    @Test
    void saveNewBeerItem()
    {
        when(beerRepository.save(any(BeerItem.class))).thenReturn(beerItem1);

        BeerItemDTO savedBeer = beerItemService.saveNewBeerItem(beerItemDTO1);

        Assertions.assertThat(savedBeer).isNotNull();

        Assertions.assertThat(savedBeer.getType()).isEqualTo(beerItemDTO1.getType());
        Assertions.assertThat(savedBeer.getName()).isEqualTo(beerItemDTO1.getName());
        Assertions.assertThat(savedBeer.getBrewery()).isEqualTo(beerItemDTO1.getBrewery());
        Assertions.assertThat(savedBeer.getAbv()).isEqualTo(beerItemDTO1.getAbv());
        Assertions.assertThat(savedBeer.getDescription()).isEqualTo(beerItemDTO1.getDescription());

    }

    @Test
    void getBeerById() throws Exception
    {
        when(beerRepository.findById(anyInt())).thenReturn(Optional.of(beerItem1));

        int beerId = 1;
        BeerItemDTO retrievedBeerItemDTO = beerItemService.getBeerById(beerId);

        Assertions.assertThat(retrievedBeerItemDTO).isNotNull();
        Assertions.assertThat(retrievedBeerItemDTO).isEqualTo(beerItemDTO1);
    }

    @Test
    void getBeerItemsWithSearch()
    {
        Page<BeerItem> beerItemsPage = new PageImpl<>(beerItems.stream().findFirst().stream().toList());
        when(beerRepository.findAllWithSearch(any(String.class),any(PageRequest.class))).thenReturn(beerItemsPage);
        String searchString = "awesome";
        Page<BeerItemDTO> beerItemsDTOPage = beerItemService.getBeerItemsWithSearch(Optional.empty(),
                                                                                    Optional.empty(),
                                                                                    Optional.empty(),
                                                                                    Optional.empty(),
                                                                                    Optional.of(searchString)
        );

        Assertions.assertThat(beerItemsDTOPage.getSize()).isEqualTo(1);

    }

    @Test
    void updateBeerItem() throws Exception
    {
        when(beerRepository.save(beerItem1)).thenReturn(beerItem1);
        when(beerRepository.findById(anyInt())).thenReturn(Optional.of(beerItem1));

        BeerItemDTO updatedBeerItemDTO = beerItemService.updateBeerItem(1, beerItemDTO1);

        Assertions.assertThat(beerItemDTO1).isEqualTo(updatedBeerItemDTO);
    }
}