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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final ITaskMapper taskMapper;
    private final ITaskRespository taskRespository;
    private final IAdminService userService;

    @Transactional
    @Override
    public ViewTaskDto createUserTask(String username, CreateTaskDto newTask) {
        User userAux = userService.findUserByUsername(username);


        Task taskAux = taskMapper.newTaskDtoToTask(newTask);
        taskAux.setUser(userAux);

        taskRespository.save(taskAux);

        return taskMapper.taskToViewTaskDto(taskAux);
    }

    @Override
    public Task findTask(Long id) {
        return taskRespository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Task not found"));
    }

    @Override
    public List<ViewTaskDto> viewAllTaskUser(String username) {
        User userAux = userService.findUserByUsername(username);

        List<Task> userTask = taskRespository.viewAllUserTask(userAux.getId());

        List<ViewTaskDto> userTaskDto = new ArrayList<>();

        userTask.forEach(e ->userTaskDto.add(taskMapper.taskToViewTaskDto(e)) );


        return userTaskDto;
    }

    @Transactional
    @Override
    public void deleteTaskUser(String username, Long idTask) {
        User userAux = userService.findUserByUsername(username);

        List<Task> taskAux = taskRespository.viewAllUserTask(userAux.getId());

        for (Task e : taskAux) {
            if (e.getUser().equals(userAux) && Objects.equals(e.getIdTask(), idTask)) {
                taskRespository.delete(e);
                return;
            }
        }


        throw new ResourceNotFoundException("Task not found");

    }

    @Transactional
    @Override
    public ViewTaskDto updateTask(String username, Long idTask, UpdateTaskDto newTaskData) {
        User userAux = userService.findUserByUsername(username);

        Task taskAux = this.findTask(idTask);

        if(!taskAux.getUser().equals(userAux)){
            throw new ResourceNotFoundException("Task not found");
        }
        
        if(newTaskData.taskName() != null){
            taskAux.setTaskName(newTaskData.taskName());
        }
        if (newTaskData.description() != null){
            taskAux.setDescription(newTaskData.description());
        }
        if (newTaskData.expireDate() != null){
            taskAux.setExpireDate(newTaskData.expireDate());
        }
        if (newTaskData.status() != null){
            TaskStatus newStatus = TaskStatus.valueOf(newTaskData.status());
            taskAux.setStatus(newStatus);
        }

        taskRespository.save(taskAux);

        return taskMapper.taskToViewTaskDto(taskAux);
    }


}
