package com.MaceSA.To_do.service;

import com.MaceSA.To_do.domain.Task;
import com.MaceSA.To_do.dto.CreateTaskDto;
import com.MaceSA.To_do.dto.UpdateTaskDto;
import com.MaceSA.To_do.dto.ViewTaskDto;

import java.util.List;

public interface ITaskService {
    ViewTaskDto createUserTask(Long idUser, CreateTaskDto newTask);
    Task findTask(Long id);
    List<ViewTaskDto> viewAllTaskUser(Long idUser);
    void deleteTaskUser(Long idUser, Long idTask);
    ViewTaskDto updateTask(Long idUser, Long idTask, UpdateTaskDto newTaskData);
}
