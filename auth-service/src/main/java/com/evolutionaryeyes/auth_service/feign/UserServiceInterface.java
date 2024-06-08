package com.evolutionaryeyes.auth_service.feign;

import com.evolutionaryeyes.auth_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("USER-SERVICE")
public interface UserServiceInterface {
    @PostMapping("api/user/saveUser")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDto);
}
