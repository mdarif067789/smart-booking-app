package com.smartbooking.app.controller;

import com.smartbooking.app.dto.LoginResponse;
import com.smartbooking.app.dto.LoginRequest;
import com.smartbooking.app.dto.RegisterRequest;
import com.smartbooking.app.dto.RegisterResponse;
import com.smartbooking.app.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User registration & login")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody(required = true) RegisterRequest req) {

        return authService.register(req);
    }

    @Operation(summary = "Login and receive JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login success"),
            @ApiResponse(responseCode = "401", description = "Bad credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody(required = true) LoginRequest req) {
        return authService.login(req);
    }
}

