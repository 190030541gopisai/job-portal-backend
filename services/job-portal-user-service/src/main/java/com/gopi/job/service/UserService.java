package com.gopi.job.service;

import com.gopi.job.dto.response.UserResponse;
import com.gopi.job.modal.User;
import com.gopi.job.payload.UpdateUserRequest;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email) throws Exception;
    User getUserById(Long id) throws Exception;
    List<User> getAllUsers();
    UserResponse updateProfile(String email, UpdateUserRequest request);

    // admin action

    UserResponse suspendUser(Long id) throws Exception;
    UserResponse activateUser(Long id) throws Exception;
    UserResponse deleteUser(Long id) throws Exception;
}
