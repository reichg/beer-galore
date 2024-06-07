package com.evolutionaryeyes.api_gateway.util;

import com.evolutionaryeyes.api_gateway.model.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    String SECRET = "nUnySPRDeX3bt54xAXFMTYc5SXDnGguzKEkof5qMhE252WJn6M9fBJXbBqkehqWeqhuFuFx99y4xrSCBgJ6J38MowTh3ekpXJMUj3T97qhGAkmyoG9whxeqhap5yroxAEhL6uW6UqSYwosaJ42jBCmaGGfE6oYAoGPwhBjEyaPpCBT9ZTwJWPWpyeh9F3D7h3JxoHCZEn3k3rWDu3p3XunEgqGcbzREuUSuRNgxbaZHdQCTDRsUNcRADUpYUR4Bh";

    public void validateToken(final String token) {
        Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
    }

    private SecretKey getSignKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(bytes);
    }


    public String extractUser(String token){
        final Claims claims = extractAllClaims(token);
        String userString = claims.get("user").toString();
        return userString;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
