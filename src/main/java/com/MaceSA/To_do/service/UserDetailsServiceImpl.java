package com.MaceSA.To_do.service;

import com.MaceSA.To_do.domain.User;
import com.MaceSA.To_do.exceptions.ResourceNotFoundException;
import com.MaceSA.To_do.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements IUserDetailsServiceImpl {
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario no encontrado"));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities( new SimpleGrantedAuthority("ROLE_"+user.getRol().name()))
                .build();
    }
}


