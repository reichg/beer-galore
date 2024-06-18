package com.evolutionaryeyes.userservice.controller;

import com.evolutionaryeyes.userservice.dto.BeerItemDTO;
import com.evolutionaryeyes.userservice.dto.UserDTO;
import com.evolutionaryeyes.userservice.dto.UserHomeDTO;
import com.evolutionaryeyes.userservice.repository.UserRepository;
import com.evolutionaryeyes.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Headers;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    Environment environment;
    @Autowired
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest context;

    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int userId) throws Exception
    {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
    }

    @PostMapping("saveUser")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDto));
    }

    @PutMapping("{userId}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int userId, @RequestBody UserDTO userDto
    ) throws Exception
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(userId, userDto));
    }

    @DeleteMapping("{userId}/delete")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable int userId) throws Exception
    {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.deleteUserById(userId));
    }

    @GetMapping("{userId}/allTriedBeers")
    public ResponseEntity<List<BeerItemDTO>> getAllTriedBeersByUserId(@PathVariable int userId
    ) throws Exception
    {
        String userHeader = context.getHeader("user");

        log.info("user: " + userHeader);
        UserDTO userHeaderDTO = mapper.readValue(userHeader, UserDTO.class);
        log.info("userDTO: " + userHeaderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllTriedBeersByUserId(userId, userHeaderDTO));
    }

    @GetMapping("{userId}/home")
    public ResponseEntity<UserHomeDTO> loadUserHomeInformation(@PathVariable int userId
    ) throws Exception
    {
        String userHeader = context.getHeader("user");

        log.info("user: " + userHeader);
        UserDTO userHeaderDTO = mapper.readValue(userHeader, UserDTO.class);
        log.info("`userDTO: " + userHeaderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userService.loadUserHomeInformation(userId, userHeaderDTO));
    }
}
