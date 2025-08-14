package com.upgrad.mini_project_two.services;

import com.upgrad.mini_project_two.dtos.CreateUserRequest;
import com.upgrad.mini_project_two.dtos.UpdateUserRequest;
import com.upgrad.mini_project_two.entities.User;
import com.upgrad.mini_project_two.enums.UserRole;
import com.upgrad.mini_project_two.exceptions.ResourceNotFoundException;
import com.upgrad.mini_project_two.exceptions.ValidationException;
import com.upgrad.mini_project_two.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_SuccessfulCreation_ReturnsSavedUser() {
        CreateUserRequest request = new CreateUserRequest("John", "johnny", "john@example.com","john", UserRole.ADMIN);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(userRepository.existsByUsername("johnny")).thenReturn(false);
        User savedUser = User.builder().id(UUID.randomUUID()).name("John").username("johnny").email("john@example.com").role(UserRole.ADMIN).build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(request);

        assertThat(result.getName()).isEqualTo("John");
        assertThat(result.getUsername()).isEqualTo("johnny");
        assertThat(result.getEmail()).isEqualTo("john@example.com");
        assertThat(result.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    void createUser_EmailAlreadyExists_ThrowsValidationException() {
        CreateUserRequest request = new CreateUserRequest("John", "johnny", "john@example.com","john", UserRole.ADMIN);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void createUser_UsernameAlreadyExists_ThrowsValidationException() {
        CreateUserRequest request = new CreateUserRequest("John", "johnny", "john@example.com","john", UserRole.ADMIN);
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(userRepository.existsByUsername("johnny")).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        List<User> users = Arrays.asList(User.builder().id(UUID.randomUUID()).build());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertThat(result).isEqualTo(users);
    }

    @Test
    void getUserById_UserExists_ReturnsUser() {
        UUID id = UUID.randomUUID();
        User user = User.builder().id(id).build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getUserById(id);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void getUserById_UserNotFound_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void getUserByEmail_UserExists_ReturnsUser() {
        String email = "john@example.com";
        User user = User.builder().id(UUID.randomUUID()).email(email).build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail(email);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void getUserByEmail_UserNotFound_ThrowsResourceNotFoundException() {
        String email = "john@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByEmail(email))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void updateUser_UpdatesAllFieldsSuccessfully() {
        UUID id = UUID.randomUUID();
        User user = User.builder().id(id).name("Old").username("olduser").email("old@e.com").role(UserRole.ADMIN).build();
        UpdateUserRequest request = new UpdateUserRequest("New", "newuser", "new@e.com", UserRole.ADMIN);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@e.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = userService.updateUser(id, request);

        assertThat(updated.getName()).isEqualTo("New");
        assertThat(updated.getUsername()).isEqualTo("newuser");
        assertThat(updated.getEmail()).isEqualTo("new@e.com");
        assertThat(updated.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    void updateUser_UsernameAlreadyExists_ThrowsValidationException() {
        UUID id = UUID.randomUUID();
        User user = User.builder().id(id).name("Old").username("olduser").email("old@e.com").role(UserRole.ADMIN).build();
        UpdateUserRequest request = new UpdateUserRequest(null, "newuser", null, null);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("newuser")).thenReturn(true);

        assertThatThrownBy(() -> userService.updateUser(id, request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void updateUser_EmailAlreadyExists_ThrowsValidationException() {
        UUID id = UUID.randomUUID();
        User user = User.builder().id(id).name("Old").username("olduser").email("old@e.com").role(UserRole.ADMIN).build();
        UpdateUserRequest request = new UpdateUserRequest(null, null, "new@e.com", null);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("new@e.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.updateUser(id, request))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void updateUser_OnlyRoleUpdated_Success() {
        UUID id = UUID.randomUUID();
        User user = User.builder().id(id).name("Old").username("olduser").email("old@e.com").role(UserRole.ADMIN).build();
        UpdateUserRequest request = new UpdateUserRequest(null, null, null, UserRole.ADMIN);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = userService.updateUser(id, request);

        assertThat(updated.getRole()).isEqualTo(UserRole.ADMIN);
    }

    @Test
    void deleteUser_UserExists_DeletesSuccessfully() {
        UUID id = UUID.randomUUID();
        when(userRepository.existsById(id)).thenReturn(true);

        userService.deleteUser(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void deleteUser_UserNotFound_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();
        when(userRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteUser(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");
    }
}