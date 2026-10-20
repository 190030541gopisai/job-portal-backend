package com.gopi.job.service.impl;

import com.gopi.job.domain.UserRole;
import com.gopi.job.domain.UserStatus;
import com.gopi.job.mapper.UserMapper;
import com.gopi.job.modal.User;
import com.gopi.job.payload.AuthResponse;
import com.gopi.job.payload.LoginRequest;
import com.gopi.job.payload.SignupRequest;
import com.gopi.job.repository.UserRepository;
import com.gopi.job.security.CustomUserDetailsService;
import com.gopi.job.security.JwtProvider;
import com.gopi.job.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public AuthResponse signup(SignupRequest request) throws Exception{
        if(userRepository.existsByEmail(request.getEmail())){
            throw new Exception("Email Already registered : " + request.getEmail());
        }

        if(request.getRole() == UserRole.ROLE_ADMIN) {
            throw new Exception("cannot self-register as role admin");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .phone(request.getPhone())
                .lastLogin(LocalDateTime.now())
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

        AuthResponse response = new AuthResponse();
        response.setTitle("welcome " + savedUser.getFullName());
        response.setMessage("Registered Successfully");
        response.setJwt(jwt);
        response.setUser(UserMapper.toDTO(savedUser));

        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}
