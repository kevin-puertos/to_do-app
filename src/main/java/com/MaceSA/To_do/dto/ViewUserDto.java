package com.MaceSA.To_do.dto;

import com.MaceSA.To_do.domain.Task;
import com.MaceSA.To_do.domain.UserRoles;

import java.util.List;

public record ViewUserDto(
        Long id,
        String username,
        UserRoles rol,
        List<ViewTaskDto> userTasks
) {}
