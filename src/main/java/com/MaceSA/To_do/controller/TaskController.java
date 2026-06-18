package com.MaceSA.To_do.controller;

import com.MaceSA.To_do.dto.CreateTaskDto;
import com.MaceSA.To_do.dto.UpdateTaskDto;
import com.MaceSA.To_do.dto.ViewTaskDto;
import com.MaceSA.To_do.service.ITaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class TaskController {

    private final ITaskService taskService;

    @PostMapping("/users/tasks")
    public ViewTaskDto createTaskUser(Authentication authentication,  @Valid @RequestBody CreateTaskDto newUserTask){
        String username = authentication.getName();
        return taskService.createUserTask(username,newUserTask);
    }


    @GetMapping("/users/tasks")
    public List<ViewTaskDto> viewAllUserTask(Authentication authentication){

        String username = authentication.getName();

        return taskService.viewAllTaskUser(username);
    }

    @DeleteMapping("/users/tasks/{idTask}")
    public String deleteUserTask(Authentication authentication, @PathVariable Long idTask){

        String username = authentication.getName();
        taskService.deleteTaskUser(username,idTask);

        return "Task successfully deleted";

    }

    @PutMapping("/users/tasks/{idTask}")
    public ViewTaskDto updateUserTask(Authentication authentication, @PathVariable Long idTask, @Valid @RequestBody UpdateTaskDto newTaskData){
        String username = authentication.getName();
        return taskService.updateTask(username,idTask,newTaskData);
    }
}
