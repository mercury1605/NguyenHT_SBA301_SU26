package com.lab56.orchid_management.services;

import com.lab56.orchid_management.dtos.*;
import com.lab56.orchid_management.entities.*;
import com.lab56.orchid_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signup(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent())
            throw new RuntimeException("Email đã được sử dụng: " + request.email());

        var user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var extraClaims = Map.<String, Object>of("role", user.getRole().name());
        return new AuthResponse(jwtService.generateToken(extraClaims, user));
    }

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = userRepository.findByEmail(request.email()).orElseThrow();
        var extraClaims = Map.<String, Object>of("role", user.getRole().name());
        return new AuthResponse(jwtService.generateToken(extraClaims, user));
    }
}
