package com.bybus.ByBus.service.impl;

import com.bybus.ByBus.dto.LoginRequest;
import com.bybus.ByBus.dto.SignupRequest;
import com.bybus.ByBus.entity.User;
import com.bybus.ByBus.repository.UserRepository;
import com.bybus.ByBus.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection (recommended)
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ================= REGISTER =================
    @Override
    public User register(SignupRequest request){

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole()); // PASSENGER / DRIVER / ADMIN
        return userRepository.save(user);
    }

    // ================= LOGIN =================
    @Override
    public User login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        return user;
    }

    // ================= FIND BY ID =================
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ================= FIND BY EMAIL =================
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
