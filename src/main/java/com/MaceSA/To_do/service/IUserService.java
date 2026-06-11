package com.MaceSA.To_do.service;

import com.MaceSA.To_do.domain.User;
import com.MaceSA.To_do.dto.*;

import java.util.List;

public interface IUserService {
     void userExist(Long id);
     void userExistUsername(String username);
     User findUser(Long id);
     User findUserByUsername(String username);
     void saveUser(User user);
     ViewUserDto createUser(CreateUserDto newUser);
     void deleteUser(Long id);
     ViewUserDto viewUser(Long id);
     List<ViewUserDto> viewAllUsers();
     ViewUserDto updateUser(Long id , UpdateUserDto newUserData);
     Boolean authenticatePassword(String plainPassword, String hashedPassword);
}
