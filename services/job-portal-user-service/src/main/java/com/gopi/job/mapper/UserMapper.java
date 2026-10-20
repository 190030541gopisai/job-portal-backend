package com.gopi.job.mapper;

import com.gopi.job.dto.response.UserResponse;
import com.gopi.job.modal.User;

public class UserMapper {
    public static UserResponse toDTO(User user) {
        if(user == null){
            return null;
        }

        UserResponse dto = new UserResponse();

        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhone(user.getPhone());
        dto.setProfileImage(user.getProfileImage());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setLastLogin(user.getLastLogin());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }
}
