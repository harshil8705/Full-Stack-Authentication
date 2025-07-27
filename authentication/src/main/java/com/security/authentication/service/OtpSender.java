package com.security.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class OtpSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailOtp(String to, String otp) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Your Signup OTP");
        mailMessage.setText("Your OTP is : " + otp);
        mailSender.send(mailMessage);

    }

}
