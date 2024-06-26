package com.sparta.padoing.controller;

import com.sparta.padoing.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

   /* @PostMapping("/signup")
    public UserResponseDto createUser(@RequestBody UserRequestDto requestDto) {
        return userService.createUser(requestDto);
    }

    @PutMapping("/users/{id}")
    public Long updateUser(@PathVariable Long id, @RequestBody UserRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }*/


}
