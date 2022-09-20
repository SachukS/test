package com.sachuk.TestTask.service;

import com.sachuk.TestTask.exception.UserAlreadyExistException;
import com.sachuk.TestTask.model.User;
import com.sachuk.TestTask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }

    public boolean checkForExist(String username) throws UserAlreadyExistException {
        User userFromDB = userRepository.findByUsername(username);
        if (userFromDB != null)
        {
            throw new UserAlreadyExistException("Username is already taken!");
        }
        return false;
    }
}
