package com.evolutionaryeyes.api_gateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String phone;
    private Integer age;
    private String roles;
}
