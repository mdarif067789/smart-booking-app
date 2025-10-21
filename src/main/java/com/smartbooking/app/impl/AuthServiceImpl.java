package com.smartbooking.app.impl;

import com.smartbooking.app.dto.LoginRequest;
import com.smartbooking.app.dto.LoginResponse;
import com.smartbooking.app.dto.RegisterRequest;
import com.smartbooking.app.dto.RegisterResponse;
import com.smartbooking.app.entity.User;
import com.smartbooking.app.enums.UserStatus;
import com.smartbooking.app.repository.UserRepository;
import com.smartbooking.app.service.AuthService;
import com.smartbooking.app.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthServiceImpl(UserRepository userRepo,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil,
                           AuthenticationManager authManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    @Override
    @Transactional
    public ResponseEntity<?> register(RegisterRequest req) {
        try {
            Optional<User> existingUser = userRepo.findByEmail(req.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "Email is already registered"));
            }

            User user = new User();
            user.setName(req.getName());
            user.setEmail(req.getEmail());
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setPhone(req.getPhone());
            user.setRole(req.getRole());

            userRepo.save(user);

            RegisterResponse response = new RegisterResponse();
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setPhone(user.getPhone());
            response.setRole(user.getRole());
            response.setRegistrationStatus(UserStatus.CREATED.name());
            response.setId(user.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception ex) {
            logger.error("Error during registration: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unexpected error occurred during registration"));
        }
    }


    @Override
    public ResponseEntity<?> login(LoginRequest req) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(),req.getPassword())
            );

            User user = userRepo.findByEmail(req.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtUtil.generateToken(user);
            LoginResponse response = new LoginResponse(token);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException ex) {
            logger.warn("Login failed for email {}: {}", req.getEmail(), ex.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } catch (UsernameNotFoundException ex) {
            logger.warn("User not found: {}", ex.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", "User not registered");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception ex) {
            logger.error("Unexpected login error: {}", ex.getMessage(), ex);
            Map<String, String> error = new HashMap<>();
            error.put("message", "Internal server error during login");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
