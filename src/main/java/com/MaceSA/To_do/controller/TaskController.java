package com.MaceSA.To_do.controller;

import com.MaceSA.To_do.dto.CreateTaskDto;
import com.MaceSA.To_do.dto.UpdateTaskDto;
import com.MaceSA.To_do.dto.ViewTaskDto;
import com.MaceSA.To_do.service.ITaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class TaskController {

    private final ITaskService taskService;

    @PostMapping("/users/{idUser}/tasks")
    public ViewTaskDto createTaskUser(@PathVariable Long idUser,  @Valid @RequestBody CreateTaskDto newUserTask){
       return taskService.createUserTask(idUser,newUserTask);
    }


    @GetMapping("/users/{idUser}/tasks")
    public List<ViewTaskDto> viewAllUserTask(@PathVariable Long idUser){


        return taskService.viewAllTaskUser(idUser);
    }

    @DeleteMapping("/users/{idUser}/tasks/{idTask}")
    public String deleteUserTask(@PathVariable Long idUser, @PathVariable Long idTask){

        taskService.deleteTaskUser(idUser,idTask);

        return "Task successfully deleted";

    }

    @PutMapping("/users/{idUser}/tasks/{idTask}")
    public ViewTaskDto updateUserTask(@PathVariable Long idUser, @PathVariable Long idTask, @Valid @RequestBody UpdateTaskDto newTaskData){
        return taskService.updateTask(idUser,idTask,newTaskData);
    }
}
