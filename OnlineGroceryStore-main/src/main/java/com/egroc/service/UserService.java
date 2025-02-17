package com.egroc.service;

import com.egroc.model.User;
import com.egroc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyPasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username); // Assumes UserRepository has this method
    }

}
