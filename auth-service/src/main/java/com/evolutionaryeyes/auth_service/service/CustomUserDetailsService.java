package com.evolutionaryeyes.auth_service.service;

import com.evolutionaryeyes.auth_service.entity.CustomUserDetails;
import com.evolutionaryeyes.auth_service.entity.UserCredential;
import com.evolutionaryeyes.auth_service.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserCredentialRepository userCredentialRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserCredential> existingUser = userCredentialRepository.findByUsername(username);

        return existingUser.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name: " + username));
    }
}
