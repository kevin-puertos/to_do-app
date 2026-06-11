package com.MaceSA.To_do.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService implements  IJwtService{
    @Value("${jwt.secret}")
    private  String secretKey;

    @Value("@{jwt.expiration}")
    private Long expirationTime;

    @Override
    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ expirationTime))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractUsername(String token) {
        return "";
    }

    @Override
    public boolean isTokenValid(String token) {
        return false;
    }
}
