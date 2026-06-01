package com.MaceSA.To_do.service;


import com.MaceSA.To_do.domain.User;
import com.MaceSA.To_do.dto.CreateUserDto;
import com.MaceSA.To_do.dto.UpdateUserDto;
import com.MaceSA.To_do.dto.ViewUserDto;
import com.MaceSA.To_do.exceptions.ResourceAlreadyExistException;
import com.MaceSA.To_do.exceptions.ResourceNotFoundException;
import com.MaceSA.To_do.mapper.IUserMapper;
import com.MaceSA.To_do.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements  IUserService{

    private  final PasswordEncoder passwordEncoder;
    private final IUserMapper userMapper;
    private final IUserRepository userRepository;


    @Override
    public void userExist(Long id) {
        User useraux = userRepository.findById(id).orElse(null);
        if (useraux != null){
            throw new ResourceAlreadyExistException("User Already Exist");
        }
    }

    @Override
    public User findUser(Long id) {

        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    @Override
    public ViewUserDto createUser(CreateUserDto newUserDto) {



        User userAux = userMapper.userDtoToUser(newUserDto);
        userAux.setPassword(passwordEncoder.encode(userAux.getPassword()));

        userRepository.save(userAux);

        return userMapper.userToViewUser(userAux);
    }
    @Transactional
    @Override
    public void deleteUser(Long id) {
        User userAux = this.findUser(id);

        userRepository.delete(userAux);

    }

    @Override
    public ViewUserDto viewUser(Long id) {
        User userAux = this.findUser(id);

        return userMapper.userToViewUser(userAux);
    }

    @Override
    public List<ViewUserDto> viewAllUsers() {
        List<User> users = userRepository.userWithTask();

        List<ViewUserDto> viewUserDtos = new ArrayList<>();

        for(User userAux : users){
            viewUserDtos.add(userMapper.userToViewUser(userAux));
        }
        return viewUserDtos;
    }

    @Transactional
    @Override
    public ViewUserDto updateUser(Long id, UpdateUserDto newUserData) {
        User userAux= this.findUser(id);

        if(newUserData.username() != null){
            userAux.setUsername(newUserData.username());
        }
        if(newUserData.password() != null){
            userAux.setPassword(passwordEncoder.encode(newUserData.password()));
        }



        userRepository.save(userAux);

        return userMapper.userToViewUser(userAux);
    }

    @Override
    public Boolean authenticatePassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword,hashedPassword);
    }
}
