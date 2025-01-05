package com.egroc.service;

import com.egroc.enums.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.egroc.model.Users;
import com.egroc.repository.UserRepository;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@gmail.com";
            String adminPassword = "123";

            // Check if the admin email exists in the repository
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                Users admin = new Users();
                admin.setEmail(adminEmail);
                admin.setUsername(adminEmail);
                admin.setFullName("Admin User");
                admin.setPassword(passwordEncoder.encode(adminPassword)); // Password encoding
                admin.setRole(Role.ADMIN);  // Assign role as Admin
                userRepository.save(admin);  // Save to the database
                System.out.println("Admin user created successfully.");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
