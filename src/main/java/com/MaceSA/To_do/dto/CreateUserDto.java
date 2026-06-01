package com.MaceSA.To_do.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(@NotBlank(message = "Username cannot be blank")
                            @Size(min = 3 , max = 30, message ="Username must be between 3 and 30 characters" )
                            String username,

                            @NotBlank(message = "Password cannot be blank")
                            @Size(min = 8,max = 30)
                            String password) {
}
