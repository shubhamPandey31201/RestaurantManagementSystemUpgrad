package com.upgrad.mini_project_two.services;

import com.upgrad.mini_project_two.dtos.CreateUserRequest;
import com.upgrad.mini_project_two.dtos.UpdateUserRequest;
import com.upgrad.mini_project_two.dtos.UserLoginDTO;
import com.upgrad.mini_project_two.entities.User;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    User createUser(CreateUserRequest request);
    List<User> getAllUsers();
    User getUserById(UUID id);
    User getUserByEmail(String email);
    User updateUser(UUID id, UpdateUserRequest request);
    void deleteUser(UUID id);

    String verifyUserCredentials( UserLoginDTO request);
}