package com.example.productmanagement.services;

import com.example.productmanagement.authentication.AuthRequest;
import com.example.productmanagement.authentication.AuthResponse;
import com.example.productmanagement.authentication.RegistrationRequest;
import com.example.productmanagement.entities.User;
import com.example.productmanagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register( RegistrationRequest registrationRequest) {
        log.info("Incoming Registration Request: {}", registrationRequest);
        User user = User.builder()
                .fName(registrationRequest.getFName())
                .lName(registrationRequest.getLName())
                .username(registrationRequest.getUsername())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role("USER")
                .build();
        log.info("first name:"+registrationRequest.getFName());
        log.info("Creating user: {}", user);
        userRepository.save(user);
        String jwtToken = jwtService.generateToken( user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        User user = userRepository.findByUsername(authRequest.getUsername());
        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

}
