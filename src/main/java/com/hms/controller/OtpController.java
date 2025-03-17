package com.hms.controller;

import com.hms.Service.JWTService;
import com.hms.Service.OtpService;
import com.hms.payload.OtpRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {

    private final OtpService otpService;
    private final JWTService jwtService;

    public OtpController(OtpService otpService, JWTService jwtService) {
        this.otpService = otpService;
        this.jwtService = jwtService;


    }
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(
            @RequestParam String phoneNumber
    ) {
        // Ensure phone number is in +91 format
        String formattedPhoneNumber = formatPhoneNumber(phoneNumber);
        // Send OTP
        otpService.sendOtp(formattedPhoneNumber);

        return ResponseEntity.ok("OTP sent to " + formattedPhoneNumber);
    }

    private String formatPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim(); // Remove spaces
        if (!phoneNumber.startsWith("+")) {
            return "+91" + phoneNumber;
        }
        return phoneNumber;
    }

// verifying Otp
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestBody OtpRequestDTO otpRequest
            ) {
        // Ensure phone number has +91 prefix
        String formattedPhoneNumber = formatPhoneNumber(otpRequest.getPhoneNumber());

        if (otpService.validateOtp(formattedPhoneNumber,otpRequest.getOtp())) {
            String token = jwtService.generateToken(formattedPhoneNumber);  // Generate JWT
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        }

        System.out.println("OTP validation failed for: " + formattedPhoneNumber);
        return ResponseEntity.status(400).body("Invalid OTP");
    }
}