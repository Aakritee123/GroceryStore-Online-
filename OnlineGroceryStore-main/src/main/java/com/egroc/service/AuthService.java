//package com.egroc.service;
//
//import com.egroc.model.User;
//import com.egroc.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import jakarta.servlet.http.HttpSession;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    public boolean loginUser(String email,  String password, HttpSession session) {
//        try {
//            // Assuming you have a method to find user by username or email
//            User user = userRepository.findByEmail(email);
//
//            // Check if user exists and password matches
//            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//                // Create an authentication token
//                Authentication authentication = new UsernamePasswordAuthenticationToken(user, password);
//                authenticationManager.authenticate(authentication);
//
//                // If authentication is successful, set the user in the session
//                session.setAttribute("user", user);
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();  // For debugging purposes
//        }
//        return false;
//    }
//
//    // Optional: Method to check if the user is logged in
//    public boolean isLoggedIn(HttpSession session) {
//        return session.getAttribute("user") != null;
//    }
//
//    public void registerUser(User user) {
//        // Encrypt the password using the password encoder
//        String hashedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(hashedPassword);  // Set the hashed password
//
//        // Save the user to the database
//        userRepository.save(user);
//    }
//
//    // Retrieve current logged-in user (if any)
/// /    public User getCurrentUser(HttpSession session) {
/// /        String email = (String) session.getAttribute("user");  // Get the email from session
/// /        if (email != null) {
/// /            return userRepository.findByEmail(email);  // Fetch the user by email
/// /        }
/// /        return null;  // No user logged in
/// /    }
//}
