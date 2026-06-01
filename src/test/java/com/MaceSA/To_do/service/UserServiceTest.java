package com.MaceSA.To_do.service;

import com.MaceSA.To_do.domain.Task;
import com.MaceSA.To_do.domain.TaskStatus;
import com.MaceSA.To_do.domain.User;
import com.MaceSA.To_do.dto.CreateUserDto;
import com.MaceSA.To_do.dto.UpdateUserDto;
import com.MaceSA.To_do.dto.ViewTaskDto;
import com.MaceSA.To_do.dto.ViewUserDto;
import com.MaceSA.To_do.exceptions.ResourceAlreadyExistException;
import com.MaceSA.To_do.exceptions.ResourceNotFoundException;
import com.MaceSA.To_do.mapper.ITaskMapper;
import com.MaceSA.To_do.mapper.IUserMapper;
import com.MaceSA.To_do.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class) //Activa Mockito en Junit5
public class UserServiceTest {

    //Crea un mock sin tocar la base de datos real
    @Mock
    private IUserRepository userRepository;

    @Mock
    private   PasswordEncoder passwordEncoder;

    @Mock
    private  IUserMapper userMapper;

    @Mock
    private ITaskMapper taskMapper;

    @InjectMocks //Crea el service e inyecta los mocks
    private  UserService userService;
    //Esto es igual a:  new UserService(userMapper, userRepository)


    @Test
    void shouldSaveUser(){
        // Arrange (preparar)
        //Crea los datos a guardar
        User user = new User();
        user.setId(1L);
        user.setUsername("Kevin");
        user.setPassword(passwordEncoder.encode("Mace.1234"));

        // Act (ejecutar)
        userService.saveUser(user);

        // Assert (verificar)
        verify(userRepository).save(user);
        verifyNoMoreInteractions(userRepository);
    }
    @Test
    void shouldCreateUser(){
        // -------- Arrange --------

        //Creo el Dto inicial, que llegaria al service
        CreateUserDto inputDto = new CreateUserDto("Kevin","Mace1234");

        //Creo el usuario que el mapper debeeria crear
        User user = new User();
        user.setUsername("Kevin");
        user.setPassword(passwordEncoder.encode(passwordEncoder.encode(inputDto.password())));

        //Creo el Resultado esperado
        ViewUserDto outputDto = new ViewUserDto(1L,"Kevin", List.of());

        //Simulamos el comportamiento del mapper
        //Cuando alguien llame al método del mapper que devuelva el user
        when(userMapper.userDtoToUser(inputDto)).thenReturn(user);

        //Testeamos el save user, simulamos la base de datos
        when(userRepository.save(user)).thenReturn(user);

        // Simulamos conversión final
        when(userMapper.userToViewUser(user)).thenReturn(outputDto);

        // -------- Act --------
        // Ejecuta el método real
        ViewUserDto result = userService.createUser(inputDto);


        // -------- Assert --------

        // 1. Validar resultado
        assertEquals("Kevin",result.username());


        // 2. Verificar interacciones
        //Verificamos que se llamó al método del mapper
        verify(userMapper).userDtoToUser(inputDto);
        //Verificamos que se llamó al método del repository
        verify(userRepository).save(user);
        //Verificamos que se llamó al método del mapper
        verify(userMapper).userToViewUser(user);
        verifyNoMoreInteractions(userRepository,userMapper);
    }
    @Test
    void shouldViewUser(){
        // -------- Arrange --------
        User user = new User(1L,"Kevin", passwordEncoder.encode("Mace.1234"), Set.of());

        ViewUserDto outPut = new ViewUserDto(1L,"Kevin", List.of());


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.userToViewUser(user)).thenReturn(outPut);

        // -------- Act --------
        ViewUserDto result = userService.viewUser(1L);


        // -------- Assert --------
        assertEquals("Kevin",result.username());

        verify(userRepository).findById(1L);
        verify(userMapper).userToViewUser(user);
        verifyNoMoreInteractions(userRepository,userMapper);
    }
    @Test
    void shouldDeleteUser(){
        // -------- Arrange --------
        User user = new User(1L,"Kevin", passwordEncoder.encode("Mace.1234"), Set.of());


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));


        // -------- Act --------
        userService.deleteUser(1L);


        // -------- Assert --------
        verify(userRepository).findById(1L);
        verify(userRepository).delete(user);
        verifyNoMoreInteractions(userRepository);
    }
    @Test
    void shouldRiseResourceExceptionDelete() {

        // -------- Arrange --------
        //simulamos que el repository no encontró el usuario, con el Optional.empty()
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());


        // -------- Act --------
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteUser(2L);
        });

        // -------- Assert --------
        verify(userRepository).findById(2L);
        assertEquals("User not found", exception.getMessage());
        verifyNoMoreInteractions(userRepository);
    }
    @Test
    void shouldViewAllUsers(){

        // -------- Arrange --------
        Task tk1 = new Task("prueba1","Esto es una prueba",LocalDate.of(2042,10,2),TaskStatus.TO_DO);
        Task tk2 = new Task("Casa","Ahorrar para una casa",LocalDate.of(2031,10,2),TaskStatus.IN_PROGRESS);

        User user1 = new User(1L,"Kevin", passwordEncoder.encode("Mace.1234"), Set.of());

        User user2= new User(2L,"Mace",passwordEncoder.encode("User2.1234"),Set.of());

        List<User> userList = List.of(user1,user2);

        //DTOs
        ViewTaskDto tk1Dto = new ViewTaskDto(1L,"prueba1",TaskStatus.TO_DO,"Esto es una prueba",LocalDate.of(2026,4,22),LocalDate.of(2042, 10 ,2));
        ViewTaskDto tk2Dto = new ViewTaskDto(1L,"Casa",TaskStatus.IN_PROGRESS,"Ahorrar para una casa",LocalDate.of(2026,4,22),LocalDate.of(2031, 10 ,2));


        ViewUserDto user1Dto = new ViewUserDto(1L,"Kevin",List.of(tk1Dto,tk2Dto));
        ViewUserDto user2Dto = new ViewUserDto(2L,"Mace",List.of());


        List<ViewUserDto> userDtos = List.of(user1Dto,user2Dto);




        when(userRepository.userWithTask()).thenReturn(userList);
        when(userMapper.userToViewUser(user1)).thenReturn(user1Dto);
        when(userMapper.userToViewUser(user2)).thenReturn(user2Dto);

        // -------- Act --------
        List<ViewUserDto> viewUserDtosList = userService.viewAllUsers();

        // -------- Assert --------
        assertEquals(2,viewUserDtosList.size());
        assertEquals("Kevin",viewUserDtosList.get(0).username());


        verify(userRepository).userWithTask();
        verify(userMapper).userToViewUser(user1);
        verify(userMapper).userToViewUser(user2);
        verifyNoMoreInteractions(userRepository,userMapper);
    }
    @Test
    void shouldUpdateUser() {
        // -------- Arrange --------

        User userAux = new User(1L,"Kevin", passwordEncoder.encode("Mace.1234"), Set.of());

        UpdateUserDto newDataUser = new UpdateUserDto("Mace","NewPassword1.23");

        ViewUserDto viewUserDto = new ViewUserDto(1L, "Mace", List.of());

        when(userRepository.findById(1L)).thenReturn(Optional.of(userAux));
        when(userMapper.userToViewUser(userAux)).thenReturn(viewUserDto);

        // -------- Act -------
        ViewUserDto result=  userService.updateUser(1L,newDataUser);

        // -------- Assert --------
        assertEquals("Mace",result.username());
        verify(userRepository).findById(1L);
        verify(userRepository).save(userAux);
        verify(userMapper).userToViewUser(userAux);
        verifyNoMoreInteractions(userRepository,userMapper);
    }
    @Test
    void shouldFindUser(){
        // -------- Arrange --------
        User user = new User(1L,"Kevin", passwordEncoder.encode("Mace.1234"), Set.of());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // -------- Act -------
        userService.findUser(1L);

        // -------- Assert --------
        verify(userRepository).findById(1L);
    }
    @Test
    void shouldUserExist(){
        // -------- Arrange --------
        User user = new User(1L,"Kevin", passwordEncoder.encode("Mace.1234"), Set.of());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // -------- Act -------

        // -------- Assert --------
        assertThrows(ResourceAlreadyExistException.class, () ->{
            userService.userExist(1L);
        });

        verify(userRepository).findById(1L);
    }
}
