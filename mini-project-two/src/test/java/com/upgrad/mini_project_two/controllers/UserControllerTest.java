package com.upgrad.mini_project_two.controllers;

import com.upgrad.mini_project_two.controllers.UserController;
import com.upgrad.mini_project_two.dtos.CreateUserRequest;
import com.upgrad.mini_project_two.dtos.UpdateUserRequest;
import com.upgrad.mini_project_two.dtos.UserResponse;
import com.upgrad.mini_project_two.entities.User;
import com.upgrad.mini_project_two.enums.UserRole;
import com.upgrad.mini_project_two.exceptions.ResourceNotFoundException;
import com.upgrad.mini_project_two.exceptions.ValidationException;
import com.upgrad.mini_project_two.services.IUserService;
import com.upgrad.mini_project_two.utils.mapperUtility.IEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private IUserService userService;
    @Mock
    private IEntityMapper entityMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ReturnsCreatedUserResponse() {
        CreateUserRequest request = new CreateUserRequest("John", "johnny","john@example.com","john", UserRole.ADMIN);
        User user = User.builder().id(UUID.randomUUID()).name("John").username("johnny").email("john@example.com").role(UserRole.ADMIN).password("john").build();
        UserResponse response = new UserResponse(user.getId(), "John", "johnny", "john@example.com", UserRole.ADMIN);

        when(userService.createUser(request)).thenReturn(user);
        when(entityMapper.toUserResponse(user)).thenReturn(response);

        ResponseEntity<UserResponse> result = userController.createUser(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void getAllUsers_ReturnsListOfUserResponses() {
        User user = User.builder().id(UUID.randomUUID()).name("A").username("a").email("a@a.com").role(UserRole.ADMIN).build();
        List<User> users = List.of(user);
        List<UserResponse> responses = List.of(new UserResponse(user.getId(), "A", "a", "a@a.com", UserRole.ADMIN));

        when(userService.getAllUsers()).thenReturn(users);
        when(entityMapper.toUserResponseList(users)).thenReturn(responses);

        ResponseEntity<List<UserResponse>> result = userController.getAllUsers();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(responses);
    }

    @Test
    void getUserById_ReturnsUserResponse() {
        UUID id = UUID.randomUUID();
        User user = User.builder().id(id).name("Jane").username("jane").email("jane@e.com").role(UserRole.ADMIN).build();
        UserResponse response = new UserResponse(id, "Jane", "jane", "jane@e.com", UserRole.ADMIN);

        when(userService.getUserById(id)).thenReturn(user);
        when(entityMapper.toUserResponse(user)).thenReturn(response);

        ResponseEntity<UserResponse> result = userController.getUserById(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void updateUser_ReturnsUpdatedUserResponse() {
        UUID id = UUID.randomUUID();
        UpdateUserRequest request = new UpdateUserRequest("New", "newuser", "new@e.com", UserRole.ADMIN);
        User user = User.builder().id(id).name("New").username("newuser").email("new@e.com").role(UserRole.ADMIN).build();
        UserResponse response = new UserResponse(id, "New", "newuser", "new@e.com", UserRole.ADMIN);

        when(userService.updateUser(id, request)).thenReturn(user);
        when(entityMapper.toUserResponse(user)).thenReturn(response);

        ResponseEntity<UserResponse> result = userController.updateUser(id, request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void deleteUser_ReturnsNoContent() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> result = userController.deleteUser(id);

        verify(userService).deleteUser(id);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(result.getBody()).isNull();
    }

    @Test
    void createUser_ValidationException_Throws() {
        CreateUserRequest request = new CreateUserRequest("John", "johnny", "john@example.com", "johh",UserRole.ADMIN);
        when(userService.createUser(request)).thenThrow(new ValidationException("Email already exists"));

        assertThatThrownBy(() -> userController.createUser(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Email already exists");
    }

    @Test
    void getUserById_UserNotFound_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        when(userService.getUserById(id)).thenThrow(new ResourceNotFoundException("User not found"));

        assertThatThrownBy(() -> userController.getUserById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void updateUser_ValidationException_Throws() {
        UUID id = UUID.randomUUID();
        UpdateUserRequest request = new UpdateUserRequest("New", "newuser", "new@e.com", UserRole.ADMIN);
        when(userService.updateUser(id, request)).thenThrow(new ValidationException("Username already exists"));

        assertThatThrownBy(() -> userController.updateUser(id, request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Username already exists");
    }

    @Test
    void deleteUser_UserNotFound_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        doThrow(new ResourceNotFoundException("User not found")).when(userService).deleteUser(id);

        assertThatThrownBy(() -> userController.deleteUser(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found");
    }
}
