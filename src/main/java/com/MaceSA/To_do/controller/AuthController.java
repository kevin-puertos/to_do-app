package com.MaceSA.To_do.controller;


import com.MaceSA.To_do.dto.CreateUserDto;
import com.MaceSA.To_do.dto.LoginDto;
import com.MaceSA.To_do.service.IJwtService;
import com.MaceSA.To_do.service.IUserDetailsServiceImpl;
import com.MaceSA.To_do.service.IAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class AuthController {
   private final IUserDetailsServiceImpl userDetailsService;
   private final IJwtService jwtService;
   private final AuthenticationManager authenticationManager;
   private final IAdminService userService;

   @PostMapping("/login")
    public ResponseEntity<String> UserLogin(@RequestBody LoginDto loginDto){
       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       loginDto.username(),
                       loginDto.password()
               )
       );
       UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.username());
       String jwt = jwtService.generateToken(userDetails);
       return  ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid CreateUserDto dto){

        userService.createUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/test")
    public String test(Authentication authentication){

        return authentication.getName();
    }

}
