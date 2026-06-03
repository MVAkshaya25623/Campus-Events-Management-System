package com.campus.events.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // Normally would use JavaMailSender
    public void sendRegistrationEmail(String toEmail, String eventTitle) {
        System.out.println("==========================================================");
        System.out.println("EMAIL SENT TO: " + toEmail);
        System.out.println("SUBJECT: Registration Confirmed for " + eventTitle);
        System.out.println("BODY: You have successfully registered for the event. Please bring your QR code!");
        System.out.println("==========================================================");
    }
    
    public void sendOtpEmail(String toEmail, String otp) {
        System.out.println("==========================================================");
        System.out.println("OTP VERIFICATION EMAIL SENT TO: " + toEmail);
        System.out.println("SUBJECT: Verify Your Account");
        System.out.println("BODY: Your 6-digit OTP is: " + otp);
        System.out.println("==========================================================");
    }
}
