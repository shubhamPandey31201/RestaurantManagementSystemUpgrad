package com.upgrad.mini_project_two.dtos;

import com.upgrad.mini_project_two.enums.UserRole;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private String name;
    private String username;

    @Email(message = "Email should be valid")
    private String email;

    private UserRole role;
}