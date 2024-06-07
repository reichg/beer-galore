package com.evolutionaryeyes.auth_service.controller;

import com.evolutionaryeyes.auth_service.dto.UserCredentialDTO;
import com.evolutionaryeyes.auth_service.dto.UserDTO;
import com.evolutionaryeyes.auth_service.feign.UserServiceInterface;
import com.evolutionaryeyes.auth_service.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserCredentialController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("register")
    public String register(@RequestBody UserCredentialDTO userCredentialDTO) {
        return authService.saveUser(userCredentialDTO);
    }

    @GetMapping("generateToken")
    public String getToken(@RequestBody UserCredentialDTO userCredentialDTO) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentialDTO.getUsername(), userCredentialDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            UserCredentialDTO existingUserCredentials = authService.findByUsername(userCredentialDTO.getUsername());
            return authService.generateToken(existingUserCredentials);
        }
        else {
            throw new Exception("invalid access");
        }
    }

    @GetMapping("validateToken")
    public String validateToken(@RequestParam String token) {
        authService.validateToken(token);
        return "valid token";
    }
}
