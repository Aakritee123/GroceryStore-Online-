package com.egroc.controller;

import com.egroc.DTO.CreateUserDTO;
import com.egroc.enums.UserRole;
import com.egroc.model.User;
import com.egroc.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    // Show the registration page (GET request)
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "Register"; // The registration form page
    }

    // Handle registration page form submission (POST request)
    @PostMapping("/register")
    public String registerUser(

            CreateUserDTO createUserDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {



        // Check for validation errors
        if (bindingResult.hasErrors()) {

            System.out.println("in registration; has errors");
            return "redirect:/register"; // If validation fails, show the form again with error messages
        }

        // Check if the email already exists
        if (registrationService.isEmailTaken(createUserDTO.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email is already registered.");
            return "redirect:/register";
        }

        // Check if passwords match
        if (!createUserDTO.getPassword().equals(createUserDTO.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/register";
        }

        System.out.println("All test passed");

        try {
            // Assign default role if not provided
            User user = new User();
            user.setName(createUserDTO.getUsername());
            user.setUsername(createUserDTO.getUsername());
            user.setEmail(createUserDTO.getEmail());
            user.setPassword(createUserDTO.getPassword());
            user.setRole(UserRole.USER);


            // Save the user into the database with encrypted password
            registrationService.registerUser(user);
            redirectAttributes.addFlashAttribute("message", "Registration successful! Please log in.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred during registration.");
            return "redirect:/register";
        }

        return "login"; // Redirect to the login page
    }
}
