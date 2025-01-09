
package com.egroc.service;
import com.egroc.model.User;
import com.egroc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyPasswordEncoder passwordEncoder;

    // Check if the email is already taken
    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email) != null;
    }

    // Register the user with an encrypted password
    public void registerUser(User user) {
        // Encrypt the password before saving to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
