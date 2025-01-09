package com.egroc.service;
import com.egroc.model.User;
import com.egroc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;  // Repository to fetch user from the database

    @Autowired
    private MyPasswordEncoder passwordEncoder;  // PasswordEncoder to verify password

    // Authenticate the user
    public User authenticateUser(String username, String password) {
        // Fetch the user by username
        User user = userRepository.findByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // If the password matches
        }
        return null; // If the user doesn't exist or password doesn't match
    }
}
