package com.MaceSA.To_do.dto;

import com.MaceSA.To_do.domain.Task;

import java.util.List;

public record ViewUserDto(
        Long id,
        String username,
        List<ViewTaskDto> userTasks
) {}
