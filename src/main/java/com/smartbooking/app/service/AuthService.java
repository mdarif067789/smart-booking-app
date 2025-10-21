package com.smartbooking.app.service;

import com.smartbooking.app.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?>  register(RegisterRequest request);
    ResponseEntity<?> login(LoginRequest request);
}

