package com.bybus.ByBus.controller;

import com.bybus.ByBus.dto.LoginRequest;
import com.bybus.ByBus.dto.SignupRequest;
import com.bybus.ByBus.entity.User;
import com.bybus.ByBus.service.AuthService;
import com.bybus.ByBus.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    // Constructor injection
    public AuthController(UserService userService,  AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    // ================= SIGN UP =================
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        User user = userService.register(request);
        return ResponseEntity.ok(user);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.login(request);
        return ResponseEntity.ok(user);
    }

    //========forgot-password==========
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {

        String email = request.get("email");

        authService.processForgotPassword(email);

        return ResponseEntity.ok(
                Map.of("message", "Password reset link sent to email")
        );
    }
}
