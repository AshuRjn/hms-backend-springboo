package com.hms.utility;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.phone_number}")
    private String twilioPhoneNumber;
    @Value("${twilio.whatsapp_number}")
    private String twilioWhatsAppNumber;

    public String sendSms(String to, String message) {
        Message sms = Message.creator(
                        new PhoneNumber(to), // Receiver's phone number
                        new PhoneNumber(twilioPhoneNumber),
                        message)
                .create();

        return sms.getSid(); // Return message SID
    }
    // New WhatsApp message method
    public String sendWhatsApp(String to, String message) {
        Message whatsappMessage = Message.creator(
                        new PhoneNumber("whatsapp:" + to),  // WhatsApp receiver
                        new PhoneNumber(twilioWhatsAppNumber),
                        message)
                .create();

        return whatsappMessage.getSid(); // Return message SID
    }
}
