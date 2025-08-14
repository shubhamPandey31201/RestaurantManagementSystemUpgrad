package com.upgrad.mini_project_two.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDTO {
    String username;
    String password;
}
