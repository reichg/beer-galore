package com.evolutionaryeyes.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserCredentialGenerateTokenDTO {
    private String username;
    private String password;
}
