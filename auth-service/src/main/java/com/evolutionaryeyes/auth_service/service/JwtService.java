package com.evolutionaryeyes.auth_service.service;

import com.evolutionaryeyes.auth_service.dto.UserCredentialDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtService {

    @Value("${client.SECRET_KEY}")
    String SECRET;

    @Autowired
    ObjectMapper mapper;

    public String generateToken(UserCredentialDTO userCredentialDTO) throws JsonProcessingException
    {
        Map<String, Object> claims = new HashMap<>();
        String userJson = mapper.writeValueAsString(userCredentialDTO);
        claims.put("user", userJson);
        log.info("putting in claim, user json: " + userJson);
        return createToken(claims, userCredentialDTO.getUsername());
    }

    public void validateToken(final String token)
    {
        Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
    }

    private String createToken(Map<String, Object> claims, String username
    )
    {
        return Jwts.builder()
                   .claims(claims)
                   .subject(username)
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                   .signWith(getSignKey())
                   .compact();
    }

    private SecretKey getSignKey()
    {

        byte[] bytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(bytes);
    }
}
