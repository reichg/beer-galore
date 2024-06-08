package com.evolutionaryeyes.api_gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${client.SECRET_KEY}")
    private String SECRET;

    public void validateToken(final String token)
    {
        Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
    }

    private SecretKey getSignKey()
    {
        byte[] bytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(bytes);
    }


    public String extractUser(String token)
    {
        final Claims claims = extractAllClaims(token);
        String userString = claims.get("user").toString();
        return userString;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver
    )
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
    }
}
