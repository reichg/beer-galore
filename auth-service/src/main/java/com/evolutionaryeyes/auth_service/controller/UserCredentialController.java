package com.evolutionaryeyes.auth_service.controller;

import com.evolutionaryeyes.auth_service.dto.UserCredentialDTO;
import com.evolutionaryeyes.auth_service.dto.UserCredentialGenerateTokenDTO;
import com.evolutionaryeyes.auth_service.feign.UserServiceInterface;
import com.evolutionaryeyes.auth_service.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public String register(@RequestBody UserCredentialDTO userCredentialDTO)
    {
        return authService.saveUser(userCredentialDTO);
    }

    @PostMapping("login")
    public ResponseEntity<UserCredentialDTO> getToken(@RequestBody UserCredentialGenerateTokenDTO userCredentials) throws Exception
    {
        log.info("hitting login");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userCredentials.getUsername(),
                userCredentials.getPassword()
        ));
        if (authentication.isAuthenticated())
        {
            UserCredentialDTO existingUserCredentials = authService.findByUsername(userCredentials.getUsername());
            existingUserCredentials.setToken(authService.generateToken(existingUserCredentials));
            return ResponseEntity.ok().body(existingUserCredentials);
            //header("token", authService.generateToken(existingUserCredentials))
        } else
        {
            throw new Exception("invalid access");
        }
    }

    @GetMapping("validate-token")
    public String validateToken(@RequestParam String token)
    {
        authService.validateToken(token);
        return "valid token";
    }
}
