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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponse register(RegistrationRequest registrationRequest) {
        try {
            boolean exist = userRepository.existsByUsername(registrationRequest.getUsername());
            if (exist){
                throw new IllegalArgumentException("User with the same username already exists.");
            }
        log.info("Incoming Registration Request: {}", registrationRequest);
        User user = User.builder()
                .lastName(registrationRequest.getLastName())
                .firstName(registrationRequest.getFirstName())
                .username(registrationRequest.getUsername())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role("USER")
                .build();
        userRepository.save(user);
        log.info("Creating user: {}", user);

         String jwtToken = jwtService.generateToken(user, user.getRole(),user.getId());
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
    catch(Exception ex){
        ex.printStackTrace(); // Just an example, you should use a proper logging mechanism.
        throw new RuntimeException("Error inserting User " + ex.getMessage());
    }}

    public AuthResponse authenticate(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        User user = userRepository.findByUsername(authRequest.getUsername());

        String jwtToken = jwtService.generateToken(userDetails, user.getRole(),user.getId());

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }









}
