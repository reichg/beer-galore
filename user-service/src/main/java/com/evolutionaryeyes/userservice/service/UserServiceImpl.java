package com.evolutionaryeyes.userservice.service;

import com.evolutionaryeyes.userservice.dto.BeerItemDTO;
import com.evolutionaryeyes.userservice.dto.UserDTO;
import com.evolutionaryeyes.userservice.dto.UserHomeDTO;
import com.evolutionaryeyes.userservice.entity.User;
import com.evolutionaryeyes.userservice.feign.BeersTriedServiceInterface;
import com.evolutionaryeyes.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


// repository accessed data needs to be mapped to a data transfer object between repo and service.
@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BeersTriedServiceInterface userServiceInterface;

    @Override
    public ResponseEntity<UserDTO> saveUser(UserDTO userDto) {
        User user = mapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        UserDTO savedUserDTO = mapper.map(savedUser, UserDTO.class);
        log.info("User saved successfully by repo: " + savedUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(int userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserDTO retrievedUserDTO = mapper.map(user.get(), UserDTO.class);
            log.info("User retrieved successfully by repo: " + retrievedUserDTO);
            return ResponseEntity.status(HttpStatus.OK).body(retrievedUserDTO);

        }
        throw new Exception("User " + userId + " does not exist");
    }

    @Override
    public ResponseEntity<UserDTO> deleteUserById(int userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserDTO deletedUserDTO = mapper.map(user.get(), UserDTO.class);
            userRepository.delete(user.get());
            log.info("User deleted successfully by repo: " + deletedUserDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedUserDTO);
        } else {
            throw new Exception("User " + userId + " does not exist");
        }
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(int userId, UserDTO userDTO) throws Exception {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            log.info("updating existing user: " + existingUser.get() + " with updated information: " + userDTO);
            existingUser.get().fromDto(userDTO);
            User updatedUser = userRepository.save(existingUser.get());
            UserDTO updatedUserDTO = mapper.map(updatedUser, UserDTO.class);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUserDTO);
        }

        throw new Exception("User: " + userId + " does not exist");


    }

    @Override
    public ResponseEntity<List<BeerItemDTO>> getAllTriedBeersByUserId(int userId, UserDTO userHeaderDTO) throws Exception {
        log.info("url userId: " + userId + " logged in userId: " + userHeaderDTO.getUserId());
        if (userId != userHeaderDTO.getUserId()) {
            throw new Exception("not allowed to access other users' beer lists.");
        }
        List<BeerItemDTO> usersBeerItemDTOs = userServiceInterface.getBeersTriedByUserId(userId).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(usersBeerItemDTOs);
    }

    @Override
    public ResponseEntity<UserHomeDTO> loadUserHomeInformation(int userId, UserDTO userHeaderDTO) throws Exception {
        if (userId != userHeaderDTO.getUserId()) {
            throw new Exception("not allowed to access other users' profile.");
        }
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            UserHomeDTO userHomeDTO = mapper.map(existingUser.get(), UserHomeDTO.class);
            List<BeerItemDTO> usersBeerItemDTOs = userServiceInterface.getBeersTriedByUserId(userId).getBody();
            userHomeDTO.setTriedBeers(usersBeerItemDTOs);
            return ResponseEntity.status(HttpStatus.OK).body(userHomeDTO);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
