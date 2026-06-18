package com.MaceSA.To_do.service;

import com.MaceSA.To_do.domain.Task;
import com.MaceSA.To_do.dto.CreateTaskDto;
import com.MaceSA.To_do.dto.UpdateTaskDto;
import com.MaceSA.To_do.dto.ViewTaskDto;

import java.util.List;

public interface ITaskService {
    ViewTaskDto createUserTask(String username, CreateTaskDto newTask);
    Task findTask(Long id);
    List<ViewTaskDto> viewAllTaskUser(String username);
    void deleteTaskUser(String username, Long idTask);
    ViewTaskDto updateTask(String username, Long idTask, UpdateTaskDto newTaskData);
}
