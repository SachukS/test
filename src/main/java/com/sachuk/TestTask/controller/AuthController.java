package com.sachuk.TestTask.controller;

import com.sachuk.TestTask.exception.UserAlreadyExistException;
import com.sachuk.TestTask.model.Role;
import com.sachuk.TestTask.model.User;
import com.sachuk.TestTask.repository.UserRepository;
import com.sachuk.TestTask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try {
            userService.checkForExist(user.getUsername());
        } catch (UserAlreadyExistException e) {
            throw new RuntimeException(e);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        if (user.getUsername().contains("admin"))
            user.setRoles(Collections.singleton(Role.ADMIN));
        else
            user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public User authenticateUser(@RequestBody User user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()));

        User loginedUser = userRepository.findByUsername(user.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return loginedUser;
    }
}
