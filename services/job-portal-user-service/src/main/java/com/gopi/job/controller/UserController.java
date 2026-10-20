package com.gopi.job.controller;

import com.gopi.job.dto.response.UserResponse;
import com.gopi.job.mapper.UserMapper;
import com.gopi.job.modal.User;
import com.gopi.job.payload.UpdateUserRequest;
import com.gopi.job.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<UserResponse> getProfile(@RequestHeader("X-User-Email") String email) throws Exception{
        User user = userService.getUserByEmail(email);

        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @PutMapping("/api/users/profile")
    public ResponseEntity<UserResponse> updateProfile(@RequestHeader("X-User-Email") String email,
                                                      @RequestBody UpdateUserRequest request) throws Exception{
        return ResponseEntity.ok(userService.updateProfile(email, request));
    }

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") Long userId) throws Exception{
        return ResponseEntity.ok(UserMapper.toDTO(userService.getUserById(userId)));
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getUsers() throws Exception{
        return ResponseEntity.ok(UserMapper.toDTO(userService.getAllUsers()));
    }

    @PatchMapping("/api/users/{userId}/suspend")
    public ResponseEntity<UserResponse> suspendUser(@PathVariable("userId") Long userId) throws Exception{
        return ResponseEntity.ok(userService.suspendUser(userId));
    }

    @PatchMapping("/api/users/{userId}/activate")
    public ResponseEntity<UserResponse> activateUser(@PathVariable("userId") Long userId) throws Exception{
        return ResponseEntity.ok(userService.activateUser(userId));
    }

    @DeleteMapping("/api/users/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable("userId") Long userId) throws Exception{
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
