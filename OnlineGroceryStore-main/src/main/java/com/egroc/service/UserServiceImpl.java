//package com.egroc.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.egroc.DTO.UserDto;
//import com.egroc.model.User;
//import com.egroc.repository.UserRepository;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public User save(UserDto userDto) {
//       // userDto.setRole("USER");
//        User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getRole(), userDto.getFullname());
//        return userRepository.save(user);
//    }
//
//}
