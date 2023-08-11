package com.example.productmanagement.authentication;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RegistrationRequest {

    private String fName;

    private String lName;
    private String username;
    private String password;

}

