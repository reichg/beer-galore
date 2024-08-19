package com.evolutionaryeyes.auth_service.service;

import com.evolutionaryeyes.auth_service.dto.UserCredentialDTO;
import com.evolutionaryeyes.auth_service.dto.UserDTO;
import com.evolutionaryeyes.auth_service.entity.UserCredential;
import com.evolutionaryeyes.auth_service.feign.UserServiceInterface;
import com.evolutionaryeyes.auth_service.repository.UserCredentialRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Override
    public String saveUser(UserCredentialDTO userCredentialDTO)
    {
        log.info(String.valueOf(userCredentialDTO));
        UserDTO userDTO = mapper.map(userCredentialDTO, UserDTO.class);
        log.info("Saving user.");
        userServiceInterface.saveUser(userDTO);
        log.info("user saved into user-info db");
        userCredentialDTO.setPassword(passwordEncoder.encode(userCredentialDTO.getPassword()));
        UserCredential userCredentialDTOToSave = mapper.map(userCredentialDTO, UserCredential.class);
        userCredentialRepository.save(userCredentialDTOToSave);
        return "User has been saved";
    }

    public String generateToken(UserCredentialDTO userCredential) throws JsonProcessingException
    {
        log.info(String.valueOf(userCredential));
        return jwtService.generateToken(userCredential);
    }

    public void validateToken(String token)
    {
        jwtService.validateToken(token);
    }

    @Override
    public UserCredentialDTO findByUsername(String username)
    {
        Optional<UserCredential> userCredential = userCredentialRepository.findByUsername(username);
        if (userCredential.isPresent())
        {
            return mapper.map(userCredential.get(), UserCredentialDTO.class);
        }
        throw new RuntimeException("user does not exist with username: " + username);
    }
}
