

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
            return "redirect:index2";

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
        return "redirect:/index";
    }
}
