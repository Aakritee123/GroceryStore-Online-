//package com.egroc.controller;
//
//import com.egroc.DTO.LoginDTO;
//import com.egroc.DTO.UserDto;
//import com.egroc.Validation.ValidationError;
//import com.egroc.enums.Role;
//
//import com.egroc.model.Users;
//import com.egroc.service.LoginService;
//import com.egroc.service.RegistrationService;
//import com.egroc.service.UserService;
//
//import com.egroc.repository.UserRepository;
//
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.egroc.enums.Role.*;
//
//@Controller
//public class LoginController {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RegistrationService registrationService;
//
//
//    // Show login page
//
//
//    // Handle login form submission
//    // Handle login form submission
//    @GetMapping("/index")
//    public String showHome(@ModelAttribute("user") Users user) {
//
//        if (user != null) {
//            System.out.println(user);
//            System.out.println(user.getEmail());
//
//            // Check the user's role and redirect accordingly
//            if (user.getRole() != null) {
//                if (user.getRole() == Role.ADMIN) {
//                    return "/admin"; // Redirect to admin dashboard
//                } else if (user.getRole() == Role.CUSTOMER) {
//                    return "redirect:/index/shop"; // Redirect to customer menu
//                }
//            }
//        }
//        return "redirect:/login";
//    }
//
//
//    // Logout method
//    @GetMapping("/logout")
//    public String logout() {
//        // Any custom logic before redirecting to the login page (optional)
//        return "redirect:/login?logout";
//    }
//
//    @GetMapping("/register")
//    public String showRegistrationPage(Model model) {
//        model.addAttribute("user", new Users());
//        return "Register"; // The registration form page
//    }
////    // Register user method
////    @PostMapping("/register")
////    public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto, Model model) throws Exception {
////        // ValidationError object to collect errors
////        ValidationError validationError = new ValidationError();
////
////        if (userDto == null) {
////            System.out.println("dto is null");
////        }
////        if (userDto != null) {
////            System.out.println("dto is not null");
////            System.out.println(userDto);
////        }
////
////
////        // Check if email is unique in both JobSeeker and Employer tables
////        Optional<Users> existingJobEmail = userService.findByEmail(userDto.getEmail());
////        if (existingJobEmail.isPresent()) {
////            validationError.setEmail("Sorry, this email is already taken.");
////        }
////
////        if (validationError.hasErrors()) {
////            // Re-add input data and errors to the model
////            model.addAttribute("userDto", userDto);
////            model.addAttribute("errorMessage", validationError);
////            return "register";
////        }
////
////        // Add new user
////        userService.addNewUser(userDto);
////
////        // Redirect to login page after registration
////        return "redirect:/login";
////    }
////    @GetMapping("/admindashboard")
////    public String adminPage(Model model, Principal principal) {
////        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
////        model.addAttribute("user", userDetails);
////        return "adminHome";
////    }
//
////    @PostMapping("/register")
////    public String registerUser(
////
////            UserDto createUserDTO,
////            BindingResult bindingResult,
////            RedirectAttributes redirectAttributes) {
////
////
////
////        // Check for validation errors
//
//    /// /        if (bindingResult.hasErrors()) {
//    /// /
//    /// /            System.out.println("in registration; has errors");
//    /// /            return "Register"; // If validation fails, show the form again with error messages
//    /// /        }
////        if (bindingResult.hasErrors()) {
////            bindingResult.getAllErrors().forEach(error -> {
////                System.out.println(error.getDefaultMessage());
////            });
////            return "Register"; // Return to registration page with errors
////        }
////
////        // Check if the email already exists
////        if (registrationService.isEmailTaken(createUserDTO.getEmail())) {
////            redirectAttributes.addFlashAttribute("error", "Email is already registered.");
////            return "Register";
////        }
////
////        // Check if passwords match
////        if (!createUserDTO.getPassword().equals(createUserDTO.getConfirmpassword())) {
////            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
////            return "Register";
////        }
////
////        System.out.println("All test passed");
////
////        try {
////            // Assign default role if not provided
////            Users user = new Users();
////            user.setFullName(createUserDTO.getUsername());
////            user.setUsername(createUserDTO.getUsername());
////            user.setEmail(createUserDTO.getEmail());
////            user.setPassword(createUserDTO.getPassword());
////            user.setRole(CUSTOMER);
////
////
////            // Save the user into the database with encrypted password
////            registrationService.registerUser(user);
////            redirectAttributes.addFlashAttribute("message", "Registration successful! Please log in.");
////        } catch (Exception e) {
////            e.printStackTrace();
////            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred during registration.");
////            return "Register";
////        }
////
////        return "login"; // Redirect to the login page
////    }
//
////    @PostMapping("/login")
////    public String login(
////            @RequestParam String email,
////            @RequestParam String password,
////            RedirectAttributes redirectAttributes) {
////
////        boolean isAuthenticated = userService.authenticateUser(email, password, passwordEncoder);
////
////        if (isAuthenticated) {
////            return "redirect:/index"; // Redirect to home on successful login
////        } else {
////            redirectAttributes.addFlashAttribute("error", "Invalid email or password.");
////            return "redirect:/login"; // Show login page with error
////        }
////    }
//
//
//    // Handle login form submission
//    @GetMapping("/login")
//    public String showloginPage() {
//        // model.addAttribute("error", "Invalid username or password");
//        return "login";
//
//    }
//
//
//    // Handle login form submission
//    @Autowired
//    LoginService loginService;
//
//    @PostMapping("/login")
//    public String loginUser(
//            @RequestParam("username") String username,
//            @RequestParam("password") String password,
//            Model model) {
//
//        // Simulate authentication failure
//        boolean authenticated = loginService.authenticateUser(username, password);
//        if (!authenticated) {
//            model.addAttribute("error", "Invalid username or password");
//            return "login";  // Redirect back to login page with error message
//        }
//
//        return "redirect:/index";  // Redirect to home after successful login
//    }
//
//
//    @PostMapping("/register")
//    public String registerUser(
//            @ModelAttribute("user") UserDto createUserDTO,
//            BindingResult bindingResult,
//            RedirectAttributes redirectAttributes) {
//
//        System.out.println("Received user data: " + createUserDTO);
//
//        if (bindingResult.hasErrors()) {
//            return "Register"; // Show form again if validation fails
//        }
//
//        if (!createUserDTO.getPassword().equals(createUserDTO.getConfirmpassword())) {
//            bindingResult.rejectValue("confirmPassword", "error.user", "Passwords do not match.");
//            return "Register";
//        }
//
//        try {
//            registrationService.registerUser(createUserDTO);
//            redirectAttributes.addFlashAttribute("message", "Registration successful! Please log in.");
//            return "redirect:/login";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "register";
//        }
//    }
//}

package com.egroc.controller;
import com.egroc.enums.UserRole;
import com.egroc.model.User;
import com.egroc.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@Controller

public class LoginController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public String showLoginForm() {

        return "login";
    }


    @PostMapping("/login")

    public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password,
                            RedirectAttributes redirectAttributes, HttpServletResponse response) {

        System.out.println("we are here");

        // Authenticate the user
        User user = authenticationService.authenticateUser(username, password);


        if (user == null) {
            // If user is not found or credentials don't match
            redirectAttributes.addFlashAttribute("error", "Invalid username or password.");
            return "redirect:/login";
        }

        // Create a secure cookie for the username (do not store the password in cookies)
        Cookie usernameCookie = new Cookie("username", username);
        usernameCookie.setMaxAge(60 * 60 * 24);
        usernameCookie.setSecure(true);
        usernameCookie.setHttpOnly(true);
        usernameCookie.setPath("/");  // Make it available across the site
        response.addCookie(usernameCookie);


        if (user.getRole() == UserRole.ADMIN) {
            return "redirect:admin";
        } else {
            return "redirect:index";

        }
    }
    @GetMapping("/logout")
    public String Logout(HttpServletRequest request,HttpServletResponse response){
        if(request.getSession(false)!=null){
            request.getSession().invalidate();
        }
        Cookie[]cookies=request.getCookies();

        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("username")){
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        return "redirect:/login";
    }
}
