package com.example.productmanagement.controllers;

import com.example.productmanagement.authentication.AuthRequest;
import com.example.productmanagement.authentication.AuthResponse;
import com.example.productmanagement.authentication.RegistrationRequest;
import com.example.productmanagement.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegistrationRequest registrationRequest){
        return this.authenticationService.register(registrationRequest);
    }

    @PostMapping("/signup")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return this.authenticationService.authenticate(authRequest);
    }

}
