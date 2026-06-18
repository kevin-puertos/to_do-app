package com.MaceSA.To_do.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;

public interface IJwtService {
    String generateToken(UserDetails user);
    SecretKey getSignInKey();
    Claims extractAllClaims(String token);
    boolean isTokenExpired(String token);
}
