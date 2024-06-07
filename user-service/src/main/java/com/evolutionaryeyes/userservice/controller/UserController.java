package com.evolutionaryeyes.userservice.controller;

import com.evolutionaryeyes.userservice.dto.BeerItemDTO;
import com.evolutionaryeyes.userservice.dto.UserDTO;
import com.evolutionaryeyes.userservice.dto.UserHomeDTO;
import com.evolutionaryeyes.userservice.repository.UserRepository;
import com.evolutionaryeyes.userservice.service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    Environment environment;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int userId) throws Exception {
        return userService.getUserById(userId);
    }

    @PostMapping("saveUser")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDto) {
        return userService.saveUser(userDto);
    }

    @PutMapping("{userId}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int userId, @RequestBody UserDTO userDto
    ) throws Exception {
        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("{userId}/delete")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable int userId) throws Exception {
        return userService.deleteUserById(userId);
    }

    @GetMapping("{userId}/allTriedBeers")
    public ResponseEntity<List<BeerItemDTO>> getAllTriedBeersByUserId(@PathVariable int userId, @RequestHeader("user") String userHeader
    ) throws Exception {
        log.info("user: " + userHeader);
        UserDTO userHeaderDTO = mapper.readValue(userHeader, UserDTO.class);
        log.info("userDTO: " + userHeaderDTO);
        return userService.getAllTriedBeersByUserId(userId, userHeaderDTO);
    }

    @GetMapping("{userId}/home")
    public ResponseEntity<UserHomeDTO> loadUserHomeInformation(@PathVariable int userId, @RequestHeader("user") String userHeader) throws Exception {
        log.info("user: " + userHeader);
        UserDTO userHeaderDTO = mapper.readValue(userHeader, UserDTO.class);
        log.info("`userDTO: " + userHeaderDTO);
        return userService.loadUserHomeInformation(userId, userHeaderDTO);
    }
}
