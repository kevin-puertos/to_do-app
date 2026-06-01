package com.MaceSA.To_do.controller;


import com.MaceSA.To_do.dto.CreateUserDto;
import com.MaceSA.To_do.dto.UpdateUserDto;
import com.MaceSA.To_do.dto.ViewUserDto;
import com.MaceSA.To_do.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/users")
    public ViewUserDto createUser(@RequestBody @Valid CreateUserDto newuser){

        return  userService.createUser(newuser);
    }

    @GetMapping("/users")
    public List<ViewUserDto> viewAllUsers(){
        return userService.viewAllUsers();
    }

    @GetMapping("/users/{id}")
    public ViewUserDto viewUserDto(@PathVariable Long id){
        return  userService.viewUser(id);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "User successfully deleted";
    }

    @PutMapping("/users/{id}")
    public ViewUserDto updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDto newUserData){
       return userService.updateUser(id,newUserData);
    }


}
