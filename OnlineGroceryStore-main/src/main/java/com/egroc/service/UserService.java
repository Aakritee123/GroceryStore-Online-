package com.egroc.service;

import java.util.List;
import java.util.Optional;

import com.egroc.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.egroc.DTO.UserDto;
import com.egroc.model.Users;
import com.egroc.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Users addNewUser(UserDto userDto) throws Exception {

        System.out.println("trying to add user");
        if (userDto == null) {
            throw new Exception("User is empty. Cannot register.");
        }

        // Check if email already exists
        if (emailExists(userDto.getEmail())) {
            throw new Exception("Email already exists.");
        }
        System.out.println("Registering user: " + userDto.getEmail());
        Users user = new Users();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.CUSTOMER);
        // user.setRole("CUSTOMER");
        System.out.println("Saving user to the database...");
        return userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    public Users authenticate(String username, String password) {
        // Fetch the user by username from the database
        Users user = userRepository.findByUsername(username);

        // Check if user exists and the password matches
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;  // Return the authenticated user
        }

        // If authentication fails, return null
        return null;
    }


    public List<Users> getAllUsers() {
        return userRepository.findAll(); // Retrieve all users
    }


    public void deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User with ID " + userId + " does not exist.");
        }
        userRepository.deleteById(userId); // Delete the user
    }


    public Users getUserById(Long id) {
        return userRepository.findByUserId(id);
    }

    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email); // Delete the user

    }
}
