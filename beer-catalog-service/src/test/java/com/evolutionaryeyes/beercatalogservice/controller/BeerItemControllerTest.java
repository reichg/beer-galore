package com.evolutionaryeyes.beercatalogservice.controller;

import com.evolutionaryeyes.beercatalogservice.dto.BeerItemDTO;
import com.evolutionaryeyes.beercatalogservice.service.BeerItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BeerItemController.class)
class BeerItemControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    BeerItemService beerItemService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void givenBeerItem_whenSaveBeerItem_thenReturnSavedBeerItem() throws Exception
    {
        BeerItemDTO beerItemDTO = BeerItemDTO.builder()
                                             .name("fun beer name")
                                             .beerItemId(1)
                                             .type("IPA")
                                             .ibu(6)
                                             .abv(6.1)
                                             .brewery("fun brewery")
                                             .description("fun ipa from a fun brewery")
                                             .build();
        when(beerItemService.saveNewBeerItem(beerItemDTO)).thenReturn(beerItemDTO);

        ResultActions response = mvc.perform(post("/api/beer/save-a-beer")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(objectMapper.writeValueAsString(beerItemDTO)));

        response.andExpect(status().isCreated()).andExpect(jsonPath("$.name").value(beerItemDTO.getName()));
    }
}