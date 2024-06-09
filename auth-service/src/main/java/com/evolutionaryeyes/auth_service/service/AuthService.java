package com.evolutionaryeyes.auth_service.service;

import com.evolutionaryeyes.auth_service.dto.UserCredentialDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AuthService {

    public String saveUser(UserCredentialDTO userCredentialDTO);

    public String generateToken(UserCredentialDTO userCredentialDTO) throws JsonProcessingException;

    public void validateToken(String token);

    UserCredentialDTO findByUsername(String username);
}
