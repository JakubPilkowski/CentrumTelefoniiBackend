package com.example.centrumtelefonii.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class ApiAndJwtAuthentication extends UsernamePasswordAuthenticationToken {
    public ApiAndJwtAuthentication(Object principal, Object credentials) {

        super(principal, credentials);
    }
}
