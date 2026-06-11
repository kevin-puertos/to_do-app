package com.MaceSA.To_do.service;

import com.MaceSA.To_do.domain.User;
import com.MaceSA.To_do.dto.LoginDto;
import com.MaceSA.To_do.exceptions.ResourceNotFoundException;
import com.MaceSA.To_do.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements IUserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario no encontrado"));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRol().name())
                .build();
    }
}


