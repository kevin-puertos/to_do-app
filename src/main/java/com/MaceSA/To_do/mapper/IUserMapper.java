package com.MaceSA.To_do.mapper;


import com.MaceSA.To_do.domain.User;
import com.MaceSA.To_do.dto.CreateUserDto;
import com.MaceSA.To_do.dto.ViewUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ITaskMapper.class)
public interface IUserMapper {

    User userDtoToUser(CreateUserDto createUserDto);
    //If I have different names in the User and DTO entity fields, I also need to specify that in the Mapper
    ViewUserDto userToViewUser(User user);



}
