package com.example.productmanagement.controllers;

import com.example.productmanagement.authentication.AuthRequest;
import com.example.productmanagement.authentication.AuthResponse;
import com.example.productmanagement.authentication.RegistrationRequest;
import com.example.productmanagement.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegistrationRequest registrationRequest){
        log.info("Received Registration Request: {}", registrationRequest);
        return this.authenticationService.register(registrationRequest);
    }

    @PostMapping("/signin")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        log.info("auth request  : {} " , authRequest);
        return this.authenticationService.authenticate(authRequest);
    }

}
