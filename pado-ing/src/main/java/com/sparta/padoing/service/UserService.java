package com.sparta.padoing.service;

import com.sparta.padoing.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    User saveOrUpdateUser(User user);
}