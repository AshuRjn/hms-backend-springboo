package com.hms.utility;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.phone_number}")
    private String twilioPhoneNumber;

    public String sendSms(String to, String message) {
        Message sms = Message.creator(
                        new PhoneNumber(to), // Receiver's phone number
                        new PhoneNumber(twilioPhoneNumber), // Twilio's phone number
                        message)
                .create();

        return sms.getSid(); // Return message SID
    }
}
