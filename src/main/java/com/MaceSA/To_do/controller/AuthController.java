package com.MaceSA.To_do.controller;


import com.MaceSA.To_do.dto.LoginDto;
import com.MaceSA.To_do.service.IJwtService;
import com.MaceSA.To_do.service.IUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class AuthController {
   private final IUserDetailsService userDetailsService;
   private final IJwtService jwtService;
   private final AuthenticationManager authenticationManager;

   @PostMapping("/login")
    public ResponseEntity<String> UserLogin(@RequestBody LoginDto loginDto){
       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       loginDto.username(),
                       loginDto.password()
               )
       );
       UserDetails userDetails = userDetailsService.loadByUsername(loginDto.username());
       String jwt = jwtService.generateToken(userDetails);
       return  ResponseEntity.ok(jwt);
    }
}
