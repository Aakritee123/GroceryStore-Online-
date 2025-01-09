package com.egroc.service;


import org.springframework.stereotype.Service;

@Service
public class MyPasswordEncoder {

    public boolean matches(String password, String password2) {
        return password.equals(password2);
    }

    public String encode(String password) {
        return password;
    }

}
