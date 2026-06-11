package com.MaceSA.To_do.service;

import com.MaceSA.To_do.dto.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserDetailsService {
     UserDetails loadByUsername(String username);
}
