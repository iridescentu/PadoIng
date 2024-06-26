package com.sparta.padoing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    public HelloController() {
        logger.info("HelloController initialized");
    }

    @GetMapping
    public String getHello() {
        return "Hello, GET!";
    }

    @PostMapping
    public String postHello(@RequestBody String message) {
        return "Hello, POST! Received: " + message;
    }
}