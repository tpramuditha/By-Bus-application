package com.bybus.ByBus.service.impl;

import com.bybus.ByBus.service.EmailNotificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final JavaMailSender mailSender;

    // Constructor Injection
    public EmailNotificationServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // ================= SEND SIMPLE EMAIL =================
    @Override
    public void sendEmail(String to, String subject, String message) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);
    }

    // ================= BUS ARRIVAL NOTIFICATION =================
    @Override
    public void sendBusArrivalNotification(String passengerEmail, String busNumber, String location) {
        String subject = "Bus Arrival Notification";
        String body = "Your bus (" + busNumber + ") is now near"
                + location + ". Please be ready. ";

        sendEmail(passengerEmail, subject, body);
    }

}
