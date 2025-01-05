package com.egroc.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.egroc.model.Users;
import com.egroc.repository.UserRepository;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserRepository usersRepository;  // Injecting UsersRepository to fetch user details

    @ModelAttribute("user")
    public Users addGlobalAttributes(Model model) {
        // Retrieve the authenticated user using SecurityContextHolder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            // Extract the email (username) of the logged-in user
            String email = ((UserDetails) principal).getUsername();
            System.out.println("Logged in user email: " + email);

            // Fetch the full user entity from the database using the email
            Optional<Users> user = usersRepository.findByEmail(email);
            if (user.isPresent()) {
                return user.get();  // Return the user object if found
            } else {
                // If no user is found, return a null or log this case
                System.out.println("User not found in database.");
            }
        }

        // If the user is not logged in or doesn't exist in the database, return null or handle as needed
        return null;  // This is a default behavior if no user is authenticated or found
    }
}

