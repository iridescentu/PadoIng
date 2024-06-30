package com.sparta.padoing.service.impl;

import com.sparta.padoing.model.User;
import com.sparta.padoing.repository.UserRepository;
import com.sparta.padoing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        System.out.println("Searching for user by username: " + username);
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveOrUpdateUser(User user) {
        System.out.println("Saving user: " + user);
        return userRepository.save(user);
    }
}