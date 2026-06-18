package com.MaceSA.To_do.service;

import com.MaceSA.To_do.domain.Task;
import com.MaceSA.To_do.domain.TaskStatus;
import com.MaceSA.To_do.domain.User;
import com.MaceSA.To_do.dto.CreateTaskDto;
import com.MaceSA.To_do.dto.UpdateTaskDto;
import com.MaceSA.To_do.dto.ViewTaskDto;
import com.MaceSA.To_do.exceptions.ResourceNotFoundException;
import com.MaceSA.To_do.mapper.ITaskMapper;
import com.MaceSA.To_do.repository.ITaskRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private ITaskMapper taskMapper;

    @Mock
    private ITaskRespository taskRespository;

    @Mock
    private IAdminService userService;


    @InjectMocks
    private TaskService taskService;


    @Test
    void  shouldFindTask(){
        // -------- Arrange --------

        Task task = new Task(1L,"Test",
                "This is a test",
                LocalDate.of(2027,3,1),
                TaskStatus.TO_DO);
        when(taskRespository.findById(1L)).thenReturn(Optional.of(task));
        // -------- Act --------
        taskService.findTask(1L);

        // -------- Assert --------
        verify(taskRespository).findById(1L);

    }

    @Test
    void shouldRiseResourceNotFoundExceptionInFindTask(){
        // -------- Arrange --------
        when(taskRespository.findById(anyLong())).thenReturn(Optional.empty());

        // -------- Act --------
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,() ->{
            taskService.findTask(anyLong());
        });

        // -------- Assert --------
        verify(taskRespository).findById(anyLong());
        assertEquals("Task not found",exception.getMessage());
        verifyNoMoreInteractions(taskRespository);
    }


    @Test
    void shouldCreateUserTask(){
        // -------- Arrange --------
        User user = new User(1L,
                "kevin",
                Set.of());

        CreateTaskDto createTaskDto = new CreateTaskDto("Test",
                TaskStatus.TO_DO,"This is a test",
                LocalDate.of(2027,3,1));

        Task task = new Task(1L,"Test",
                "This is a test",
                LocalDate.of(2027,3,1),
                TaskStatus.TO_DO);

        ViewTaskDto viewTaskDto = new ViewTaskDto(1L,
                "Test",
                TaskStatus.TO_DO,
                "This is a test",
                LocalDate.of(2026,4,27),
                LocalDate.of(2027,3,1));

        when(userService.findUser(anyLong())).thenReturn(user);
        when(taskMapper.newTaskDtoToTask(createTaskDto)).thenReturn(task);
        when(taskRespository.save(task)).thenReturn(task);
        when(taskMapper.taskToViewTaskDto(task)).thenReturn(viewTaskDto);

        // --------   Act   --------
        ViewTaskDto result = taskService.createUserTask(1L,createTaskDto);

        // -------- Assert  --------
        assertNotNull(result);
        assertEquals(viewTaskDto,result);

        verify(userService).findUser(1L);
        verify(taskRespository).save(task);
        verify(taskMapper).newTaskDtoToTask(createTaskDto);
        verify(taskMapper).taskToViewTaskDto(task);

        //Verifica si el la tarea se guardó y asocio al usuario
        assertTrue(user.getUserTasks().contains(task));
    }

    @Test
    void shouldViewAllTaskUser(){
        // -------- Arrange --------
        User user = new User(1L,
                "kevin",
                Set.of());

        List<Task> userTask = new ArrayList<>();
        Task task1 = new Task(
                1L,
                "Test",
                "Test with Mockito",
                LocalDate.of(2027,5,29),
                TaskStatus.TO_DO);

        Task task2 = new Task(
                2L,
                "Test 2",
                "Test with Mockito 2",
                LocalDate.of(2029,5,9),
                TaskStatus.TO_DO);

        userTask.add(task1);
        userTask.add(task2);

        ViewTaskDto viewTaskDto1 = new ViewTaskDto(1L,
                "Test",
                TaskStatus.TO_DO,
                "Test with Mockito",
                LocalDate.of(2026,4,29),
                LocalDate.of(2027,5,29)
                );
        ViewTaskDto viewTaskDto2 = new ViewTaskDto(
                2L,
                "Test 2",
                TaskStatus.TO_DO,
                "Test with Mockito 2",
                LocalDate.of(2026,4,29),
                LocalDate.of(2029,5,9)
        );

        when(userService.findUser(anyLong())).thenReturn(user);
        when(taskRespository.viewAllUserTask(1L)).thenReturn(userTask);
        when(taskMapper.taskToViewTaskDto(task1)).thenReturn(viewTaskDto1);
        when(taskMapper.taskToViewTaskDto(task2)).thenReturn(viewTaskDto2);

        // --------   Act   --------
        List<ViewTaskDto> result = taskService.viewAllTaskUser(1L);

        // -------- Assert  --------
        assertEquals("Test",result.get(0).taskName());
        assertEquals("Test 2",result.get(1).taskName());


        verify(userService).findUser(1L);
        verify(taskRespository).viewAllUserTask(1L);
        verify(taskMapper,times(2)).taskToViewTaskDto(any());

    }

    @Test
    void shouldDeleteTask(){
        // -------- Arrange --------
        User user = new User(1L,
                "kevin",
                Set.of());

        Task task = new Task(1L,"Test",
                "This is a test",
                LocalDate.of(2027,3,1),
                TaskStatus.TO_DO);
        Task task2 = new Task(2L,"Test 2",
                "This is a test 2",
                LocalDate.of(2028,5,7),
                TaskStatus.TO_DO);


        List<Task> userTasks = new ArrayList<>();
        userTasks.add(task);
        userTasks.add(task2);



        when(userService.findUser(1L)).thenReturn(user);
        when(taskRespository.viewAllUserTask(1L)).thenReturn(userTasks);

        // --------   Act   --------
        taskService.deleteTaskUser(1L,2L);

        // -------- Assert  --------
        verify(taskRespository).delete(task2);
    }

    @Test
    void shouldRiseResourceNotFoundExceptionDeleteTask(){
        User user = new User(1L,
                "kevin",
                Set.of());

        when(userService.findUser(anyLong())).thenReturn(user);
        when(taskRespository.viewAllUserTask(anyLong())).thenReturn(List.of());

        // --------   Act   --------

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class,() ->{
            taskService.deleteTaskUser(anyLong(),anyLong());
        });

        // -------- Assert  --------
        assertEquals("Task not found",result.getMessage());

        verify(userService).findUser(anyLong());
        verify(taskRespository).viewAllUserTask(anyLong());
        verifyNoMoreInteractions(taskRespository);
    }



    @Test
    void shouldUpdateTask(){
        // -------- Arrange --------
        User user = new User(1L,
                "kevin",
                Set.of());

        UpdateTaskDto newDataTask = new UpdateTaskDto(
                "Hi",
                "IN_PROGRESS",
                "Updated",
                LocalDate.of(2029,7,12));

        Task taskAux = new Task(
                1L,
                "Old name",
                "Old description",
                LocalDate.of(20024,7,12),
                TaskStatus.IN_PROGRESS
                );

        ViewTaskDto viewTaskDto = new ViewTaskDto(
                1L,
                "Hi",
                TaskStatus.IN_PROGRESS,
                "Updated",
                LocalDate.of(2026,4,28),
                LocalDate.of(2029,7,12)
        );

        when(taskRespository.findById(1L)).thenReturn(Optional.of(taskAux));
        when(userService.findUser(1L)).thenReturn(user);
        when(taskMapper.taskToViewTaskDto(taskAux)).thenReturn(viewTaskDto);
        // --------   Act   --------

        ViewTaskDto result =  taskService.updateTask(1L,1L,newDataTask);


        // -------- Assert  --------

        assertEquals("Hi", taskAux.getTaskName());
        assertEquals("Updated", taskAux.getDescription());
        assertEquals(LocalDate.of(2029, 7, 12), taskAux.getExpireDate());
        assertEquals(TaskStatus.IN_PROGRESS, taskAux.getStatus());



        verify(userService).findUser(1L);
        verify(taskRespository).findById(1L);
        verify(taskRespository).save(taskAux);
        verify(taskMapper).taskToViewTaskDto(taskAux);


        assertEquals(viewTaskDto,result);

    }

    @Test
    void shouldRiseResourceNotFoundExceptionUpdateTask(){
        // -------- Arrange --------

        User user = new User(1L,
                "kevin",
                Set.of());

        UpdateTaskDto newDataTask = new UpdateTaskDto(
                "Hi",
                "IN_PROGRESS",
                "Updated",
                LocalDate.of(2029,7,12));

        User user2 = new User(2L,
                "Mace",
                Set.of());


        Task taskAux = new Task(
                1L,
                "Old name",
                "Old description",
                LocalDate.of(20024,7,12),
                TaskStatus.IN_PROGRESS
        );
        taskAux.setUser(user2);



        when(userService.findUser(1L)).thenReturn(user);
        when(taskRespository.findById(1L)).thenReturn(Optional.of(taskAux));

        // --------   Act   --------

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class,()->{
            taskService.updateTask(1L,1L,newDataTask);
        });

        // -------- Assert  --------
        assertEquals("Task not found",result.getMessage());

        verify(userService).findUser(1L);
        verify(taskRespository).findById(1L);
    }




}
