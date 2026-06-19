package com.MaceSA.To_do.dto;

import com.MaceSA.To_do.domain.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record CreateTaskDto(
        @Size(min =3 ,max = 60, message = "Task name must be between 3 and 60 characters")
        @NotBlank(message = "Task name cannot be blank")
        String taskName,
        @Enumerated(EnumType.STRING)
        @Pattern(regexp = "TO_DO|IN_PROGRESS|CANCELED|COMPLETED",message = "Invalid status, allowed status: TO_DO, IN_PROGRESS, CANCELED, COMPLETED")
        String status,
        @Size(min =3 ,max = 150, message = "Task name must be between 3 and 150 characters")
        String description,
        @JsonProperty("expiredate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Future(message = "Task date must be in te future")
        LocalDate expireDate) { }
