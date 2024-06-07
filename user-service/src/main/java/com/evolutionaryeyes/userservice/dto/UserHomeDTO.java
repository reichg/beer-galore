package com.evolutionaryeyes.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserHomeDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phone;
    private Integer age;
//    private String password;
    private String roles;

    List<BeerItemDTO> triedBeers;
}
