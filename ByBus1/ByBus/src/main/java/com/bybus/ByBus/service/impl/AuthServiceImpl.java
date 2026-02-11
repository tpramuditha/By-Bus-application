package com.bybus.ByBus.service.impl;

import com.bybus.ByBus.entity.User;
import com.bybus.ByBus.repository.UserRepository;
import com.bybus.ByBus.service.AuthService;
import com.bybus.ByBus.service.EmailNotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final EmailNotificationService emailService;

    public AuthServiceImpl(UserRepository userRepository,
                           EmailNotificationService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
    @Override
    public void processForgotPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate reset token
        String token = UUID.randomUUID().toString();

        // Save token
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));

        userRepository.save(user);

        // Reset link
        String resetLink =
                "http://localhost:8080/frontend/public/reset-password.html?token=" + token;

        emailService.sendEmail(
                user.getEmail(),
                "ByBus - Reset Password",
                "Click the link below to reset your password:\n\n" + resetLink
        );
    }
}
