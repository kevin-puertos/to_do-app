package com.MaceSA.To_do.mapper;

import com.MaceSA.To_do.domain.Task;
import com.MaceSA.To_do.dto.CreateTaskDto;
import com.MaceSA.To_do.dto.ViewTaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITaskMapper {
    //NewTaskDto->Task
    Task newTaskDtoToTask(CreateTaskDto newTaskDto);

    //Task -> ViewTaskDto
    ViewTaskDto taskToViewTaskDto(Task task);

}
