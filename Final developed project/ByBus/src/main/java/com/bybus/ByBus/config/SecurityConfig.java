package com.bybus.ByBus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for development (IMPORTANT: enable later for production)
                .csrf(csrf -> csrf.disable())

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/assets/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico",
                                "/frontend/**"
                        ).permitAll()

                        .requestMatchers(

                                "/",
                                "/index.html",
                                "/assets/**",
                                "/public/**",
                                "/pages/**",
                                "/api/passenger/**",
                                "/api/bookings/**",
                                "/api/buses/**",
                                "/api/admin/**",
                                "/api/auth/**",   // login & signup
                                "/ws/**",         // websocket endpoint
                                "/topic/**"       // websocket subscriptions
                        ).permitAll()

                        .anyRequest().authenticated()
                );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
