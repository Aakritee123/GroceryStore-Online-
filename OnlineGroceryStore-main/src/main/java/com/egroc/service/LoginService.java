package com.egroc.service;

import com.egroc.model.User;
import com.egroc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyPasswordEncoder passwordEncoder;

    // Authenticate user with password encryption (using email)
    public boolean authenticateUser(String email, String password) {
        // Retrieve user from the database by email
        User user = userRepository.findByEmail(email);  // Finding user by email

        // If user doesn't exist, return false
        if (user == null) {
            return false;
        }

        // Compare the entered password with the stored hashed password
        return passwordEncoder.matches(password, user.getPassword());
    }
}