package com.upgrad.mini_project_two.services;

import com.upgrad.mini_project_two.dtos.UserLoginDTO;
import com.upgrad.mini_project_two.exceptions.ValidationException;
import com.upgrad.mini_project_two.dtos.CreateUserRequest;
import com.upgrad.mini_project_two.dtos.UpdateUserRequest;
import com.upgrad.mini_project_two.entities.User;
import com.upgrad.mini_project_two.dtos.UserPrincipal;
import com.upgrad.mini_project_two.exceptions.ResourceNotFoundException;
import com.upgrad.mini_project_two.repositories.UserRepository;
import com.upgrad.mini_project_two.utils.securityUtils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User createUser(CreateUserRequest request) {
        log.info("Creating user with email: {} and username: {}", request.getEmail(), request.getUsername());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("User with email " + request.getEmail() + " already exists");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ValidationException("User with username " + request.getUsername() + " already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .role(request.getRole())
                .password(encoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);
        log.info("User created successfully with id: {}", savedUser.getId());

        log.info("Saved User from DB: {}", savedUser);

        return savedUser;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(UUID id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        log.info("Fetching user with email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    public User updateUser(UUID id, UpdateUserRequest request) {
        log.info("Updating user with id: {}", id);

        User user = getUserById(id);

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getUsername() != null) {
            if (!user.getUsername().equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
                throw new ValidationException("User with username " + request.getUsername() + " already exists");
            }
            user.setUsername(request.getUsername());
        }
        if (request.getEmail() != null) {
            if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                throw new ValidationException("User with email " + request.getEmail() + " already exists");
            }
            user.setEmail(request.getEmail());
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with id: {}", updatedUser.getId());
        return updatedUser;
    }

    public void deleteUser(UUID id) {
        log.info("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
        log.info("User deleted successfully with id: {}", id);
    }

    public String verifyUserCredentials(UserLoginDTO userLoginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
        User user = userRepository.findByUsername(userLoginDTO.getUsername()).orElse(null);
        if(authentication.isAuthenticated() && user!=null ){
            return jwtUtil.generateToken(new UserPrincipal(user));
        }
        else if(!authentication.isAuthenticated()){
            throw new AccessDeniedException("Failed to authenticate verify whether the details are correct");
        }
        else{
            throw new ResourceNotFoundException("Internal Server error please login later");
        }
    }
}