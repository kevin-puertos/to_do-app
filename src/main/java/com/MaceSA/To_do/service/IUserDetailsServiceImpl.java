package com.MaceSA.To_do.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface IUserDetailsServiceImpl extends UserDetailsService {
     UserDetails loadUserByUsername(String username);
}
