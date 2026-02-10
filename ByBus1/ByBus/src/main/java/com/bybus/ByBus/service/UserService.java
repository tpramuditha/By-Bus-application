package com.bybus.ByBus.service;

import com.bybus.ByBus.dto.LoginRequest;
import com.bybus.ByBus.dto.SignupRequest;
import com.bybus.ByBus.entity.User;

public interface UserService {
    //Register a new user (PASSENGER / DRIVER / ADMIN)
    User register(SignupRequest request);

    //Login user using email & password
    User login(LoginRequest request);

    //Find user by ID
    User findById(Long id);

    //Find user by email
    User findByEmail(String email);
}
