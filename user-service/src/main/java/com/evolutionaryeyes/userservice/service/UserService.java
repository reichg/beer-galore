package com.evolutionaryeyes.userservice.service;

import com.evolutionaryeyes.userservice.dto.BeerItemDTO;
import com.evolutionaryeyes.userservice.dto.UserDTO;
import com.evolutionaryeyes.userservice.dto.UserHomeDTO;
import com.evolutionaryeyes.userservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    ResponseEntity<UserDTO> saveUser(UserDTO userDto);

    ResponseEntity<UserDTO> getUserById(int userId) throws Exception;

    ResponseEntity<UserDTO> deleteUserById(int userId) throws Exception;

    ResponseEntity<UserDTO> updateUser(int userId, UserDTO updatedUser) throws Exception;

    ResponseEntity<List<BeerItemDTO>> getAllTriedBeersByUserId(int userId, UserDTO userHeaderDTO
    ) throws Exception;

    ResponseEntity<UserHomeDTO> loadUserHomeInformation(int userId, UserDTO userHeaderDTO) throws Exception;
}
