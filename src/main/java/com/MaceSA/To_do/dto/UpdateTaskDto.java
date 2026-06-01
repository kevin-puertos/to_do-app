package com.MaceSA.To_do.dto;

import com.MaceSA.To_do.domain.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateTaskDto(@Size(min =3 ,max = 60, message = "Task name must be between 3 and 60 characters")
                            String taskName,

                            @Pattern(regexp = "TO_DO|IN_PROGRESS|CANCELED|COMPLETED",message = "Invalid status, allowed status: TO_DO, IN_PROGRESS, CANCELED, COMPLETED")
                            String status,

                            @Size(min =3 ,max = 150, message = "Task name must be between 3 and 150 characters")
                            String description,

                            @Future(message = "Task date must be in te future")
                            LocalDate expireDate) {
}
