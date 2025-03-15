package com.hms.utility;

import com.hms.entity.Booking;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendBookingConfirmation(String toEmail, String pdfPath, Booking booking) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("Booking Confirmation - The Emerald Resort");
        helper.setText("Dear " + booking.getName() + ",\n\n"
                + "Your booking at *The Emerald Resort* is confirmed! 🎉\n"
                + "Check-in: " + booking.getCheckInDate() + "\n"
                + "Check-out: " + booking.getCheckOutDate() + "\n\n"
                + "We have attached your booking confirmation PDF.\n\n"
                + "For assistance, call +1-800-123-4567.\n\n"
                + "Best regards,\n"
                + "The Emerald Resort Team");

        // Attach PDF
        FileSystemResource file = new FileSystemResource(new File(pdfPath));
        helper.addAttachment("Booking_Confirmation.pdf", file);

        mailSender.send(message);
    }
}
