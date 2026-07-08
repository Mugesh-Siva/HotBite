package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.*;
import com.hexaware.hotbyte.entity.User;
import com.hexaware.hotbyte.repository.UserRepository;
import com.hexaware.hotbyte.security.UserDetailsImpl;
import com.hexaware.hotbyte.service.impl.UserDetailsServiceImpl;
import com.hexaware.hotbyte.service.UserService;
import com.hexaware.hotbyte.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * POST /api/auth/register - Register a new user and return JWT
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequestDTO dto) {
        log.info("Register endpoint called for email: {}", dto.getEmail());
        try {
            UserResponseDTO created = userService.createUser(dto);
            // Load user details and generate token
            UserDetails userDetails = userDetailsService.loadUserByUsername(created.getEmail());
            String token = jwtUtil.generateToken(userDetails);

            AuthResponse response = new AuthResponse(
                    token,
                    created.getUserId(),
                    created.getEmail(),
                    created.getFullName(),
                    created.getRoleName(),
                    created.getRoleId()
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Registration failed: {}", e.getMessage());
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST /api/auth/login - Authenticate user and return JWT
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthLoginRequest request) {
        log.info("Login endpoint called for email: {}", request.getEmail());
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetailsImpl UserDetailsImpl = (UserDetailsImpl) auth.getPrincipal();
            String token = jwtUtil.generateToken(UserDetailsImpl);

            // Fetch user details from DB for response
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            String roleName = user.getRole() != null ? user.getRole().getRoleName() : "CUSTOMER";
            Integer roleId = user.getRole() != null ? user.getRole().getRoleId() : 2;

            AuthResponse response = new AuthResponse(
                    token,
                    user.getUserId(),
                    user.getEmail(),
                    user.getFullName(),
                    roleName,
                    roleId
            );
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            log.warn("Login failed for email: {} - bad credentials", request.getEmail());
            return new ResponseEntity<>(Map.of("error", "Invalid email or password"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage());
            return new ResponseEntity<>(Map.of("error", "Login failed: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /api/auth/me - Return current authenticated user's details
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(Map.of("error", "Not authenticated"), HttpStatus.UNAUTHORIZED);
        }
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(Map.of("error", "User not found"), HttpStatus.NOT_FOUND);
        }
        String roleName = user.getRole() != null ? user.getRole().getRoleName() : "CUSTOMER";
        Integer roleId = user.getRole() != null ? user.getRole().getRoleId() : 2;

        UserResponseDTO dto = new UserResponseDTO(
                user.getUserId(), roleId, roleName,
                user.getFullName(), user.getEmail(),
                user.getContactNumber(), user.getGender(),
                user.getIsActive(), user.getCreatedAt()
        );
        return ResponseEntity.ok(dto);
    }
}
