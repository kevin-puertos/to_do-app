package com.MaceSA.To_do.controller;


import com.MaceSA.To_do.dto.UpdateUserDto;
import com.MaceSA.To_do.dto.ViewUserDto;
import com.MaceSA.To_do.service.IAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminController {
    private final IAdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<ViewUserDto> viewAllUsers(){
        return adminService.viewAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ViewUserDto viewUserDto(@PathVariable Long id){
        return  adminService.viewUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id){
        adminService.deleteUser(id);
        return "User successfully deleted";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ViewUserDto updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDto newUserData){
       return adminService.updateUser(id,newUserData);
    }


}
