package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.ForgotPasswordRequest;
import com.hexaware.hotbyte.dto.ResetPasswordRequest;
import com.hexaware.hotbyte.dto.VerifyOtpRequest;
import com.hexaware.hotbyte.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            userService.generateOtpAndSendEmail(request.getEmail());
            return ResponseEntity.ok().body("{\"message\": \"OTP sent to your email successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        try {
            boolean isValid = userService.verifyOtp(request.getEmail(), request.getOtp());
            if (isValid) {
                return ResponseEntity.ok().body("{\"message\": \"OTP verified successfully\"}");
            } else {
                return ResponseEntity.badRequest().body("{\"error\": \"Invalid or expired OTP\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        try {
            userService.resetPassword(request.getEmail(), request.getOtp(), request.getNewPassword());
            return ResponseEntity.ok().body("{\"message\": \"Password reset successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
