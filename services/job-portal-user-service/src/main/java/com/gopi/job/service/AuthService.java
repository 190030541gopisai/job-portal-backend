package com.gopi.job.service;

import com.gopi.job.payload.AuthResponse;
import com.gopi.job.payload.LoginRequest;
import com.gopi.job.payload.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest request) throws Exception;
    AuthResponse login(LoginRequest request);
}
