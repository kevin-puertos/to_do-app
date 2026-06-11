package com.MaceSA.To_do.service;

import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.security.PrivateKey;

public interface IJwtService {
    String generateToken(UserDetails user);
    SecretKey getSignInKey();
    String extractUsername(String token);
    boolean isTokenValid(String token);
}
