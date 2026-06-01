package com.MaceSA.To_do.dto;

import com.MaceSA.To_do.domain.TaskStatus;
import com.MaceSA.To_do.domain.User;

import java.time.LocalDate;

public record ViewTaskDto (
        Long idTask,
        String taskName,
        TaskStatus status,
        String description,
        LocalDate creationDate,
        LocalDate expireDate) { }