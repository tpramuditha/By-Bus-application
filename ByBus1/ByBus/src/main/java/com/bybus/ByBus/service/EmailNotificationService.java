package com.bybus.ByBus.service;

public interface EmailNotificationService {
    // Send simple email
    void sendEmail(String to, String subject, String message);

    //Send bus arrival notification to passenger
    void sendBusArrivalNotification(String passengerEmail,
                                    String busNumber,
                                    String location);
}
