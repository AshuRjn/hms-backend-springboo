package com.hms.Service;

import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    @Value("${twilio.phone_number}")
    private String twilioPhoneNumber;

    private final Map<String, String> otpStorage = new HashMap<>();  // Temporary OTP storage

    public void sendOtp(String phoneNumber) {
        String otp = generateOtp();
        otpStorage.put(phoneNumber, otp);  // Store OTP temporarily

        String messageBody ="HMS Security Alert: Your OTP is " +otp+
                " This code is valid for 5 minutes. Never share your OTP with anyone.";

        Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                messageBody
        ).create();
    }

    public boolean validateOtp(String phoneNumber, String otp) {
        return otp.equals(otpStorage.get(phoneNumber));  // Validate OTP
    }

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000)); // Generate 6-digit OTP
    }
}
