package com.evolutionaryeyes.userservice.service;

import com.evolutionaryeyes.userservice.dto.BeerItemDTO;
import com.evolutionaryeyes.userservice.dto.UserDTO;
import com.evolutionaryeyes.userservice.dto.UserHomeDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserDTO saveUser(UserDTO userDto);

    UserDTO getUserById(int userId) throws Exception;

    UserDTO deleteUserById(int userId) throws Exception;

    UserDTO updateUser(int userId, UserDTO updatedUser
    ) throws Exception;

    Page<BeerItemDTO> getAllTriedBeersByUserId(int userId, UserDTO userHeaderDTO
    ) throws Exception;

    UserHomeDTO loadUserHomeInformation(int userId, UserDTO userHeaderDTO
    ) throws Exception;
}
