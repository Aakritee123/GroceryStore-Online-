package com.egroc.controller;

import com.egroc.DTO.UserDto;
import com.egroc.Validation.ValidationError;
import com.egroc.enums.Role;

import com.egroc.model.Users;
import com.egroc.service.UserService;

import com.egroc.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

import static com.egroc.enums.Role.*;

@Controller
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;





    // Show login page
    @GetMapping("/login")
    public String login() {
        return "login";

    }

    // Handle login form submission
    // Handle login form submission
    @GetMapping("/index")
    public String showHome(@ModelAttribute("user") Users user) {

        if (user != null) {
            System.out.println(user);
            System.out.println(user.getEmail());

            // Check the user's role and redirect accordingly
            if (user.getRole() != null) {
                if (user.getRole() == Role.ADMIN) {
                    return "/admin"; // Redirect to admin dashboard
                } else if (user.getRole() == Role.CUSTOMER) {
                    return "redirect:/index/shop"; // Redirect to customer menu
                }
            }
        }
        return "redirect:/login";
    }


    // Logout method
    @GetMapping("/logout")
    public String logout() {
        // Any custom logic before redirecting to the login page (optional)
        return "redirect:/login?logout";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("userDto", new UserDto()); // Empty User object to bind form fields
        return "register";  // Return the Thymeleaf template for registration
    }

    // Register user method
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDto") UserDto userDto, Model model) throws Exception {
        // ValidationError object to collect errors
        ValidationError validationError = new ValidationError();

        if (userDto == null) {
            System.out.println("dto is null");
        }
        if (userDto != null) {
            System.out.println("dto is not null");
            System.out.println(userDto);
        }


        // Check if email is unique in both JobSeeker and Employer tables
        Optional<Users> existingJobEmail = userService.findByEmail(userDto.getEmail());
        if (existingJobEmail.isPresent()) {
            validationError.setEmail("Sorry, this email is already taken.");
        }

        if (validationError.hasErrors()) {
            // Re-add input data and errors to the model
            model.addAttribute("userDto", userDto);
            model.addAttribute("errorMessage", validationError);
            return "register";
        }

        // Add new user
        userService.addNewUser(userDto);

        // Redirect to login page after registration
        return "redirect:/login";
    }
//    @GetMapping("/admindashboard")
//    public String adminPage(Model model, Principal principal) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//        model.addAttribute("user", userDetails);
//        return "adminHome";
//    }



}