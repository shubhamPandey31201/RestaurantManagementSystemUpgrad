package com.upgrad.mini_project_two.controllers;

import com.upgrad.mini_project_two.dtos.CreateUserRequest;
import com.upgrad.mini_project_two.dtos.UpdateUserRequest;
import com.upgrad.mini_project_two.dtos.UserLoginDTO;
import com.upgrad.mini_project_two.dtos.UserResponse;
import com.upgrad.mini_project_two.entities.User;
import com.upgrad.mini_project_two.services.IUserService;
import com.upgrad.mini_project_two.utils.mapperUtility.IEntityMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;
    private final IEntityMapper entityMapper;

    @Autowired
    UserController(IUserService userService, IEntityMapper entityMapper) {
        this.userService = userService;
        this.entityMapper = entityMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        logger.info("Entered createUser with request: {}", request);
        User user = userService.createUser(request);
        UserResponse response = entityMapper.toUserResponse(user);
        logger.info("User created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO request) {
        logger.info("Entered loginUser with request: {}", request);
        String token = userService.verifyUserCredentials(request);
        logger.info("User login successful, token generated");
        return ResponseEntity.ok(token);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        logger.info("Entered getAllUsers");
        List<User> users = userService.getAllUsers();
        List<UserResponse> response = entityMapper.toUserResponseList(users);
        logger.info("Fetched all users successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        logger.info("Entered getUserById with id: {}", id);
        User user = userService.getUserById(id);
        UserResponse response = entityMapper.toUserResponse(user);
        logger.info("Fetched user successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request) {
        logger.info("Entered updateUser with id: {} and request: {}", id, request);
        User user = userService.updateUser(id, request);
        UserResponse response = entityMapper.toUserResponse(user);
        logger.info("User updated successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        logger.info("Entered deleteUser with id: {}", id);
        userService.deleteUser(id);
        logger.info("User deleted successfully for id: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}