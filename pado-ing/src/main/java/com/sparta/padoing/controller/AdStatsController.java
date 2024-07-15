package com.sparta.padoing.controller;

import com.sparta.padoing.dto.request.StatsRequest;
import com.sparta.padoing.dto.response.AdStatsResponseDto;
import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.service.AdStatsService;
import com.sparta.padoing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stats/ads")
public class AdStatsController {

    @Autowired
    private AdStatsService adStatsService;

    @Autowired
    private UserService userService;

    @PostMapping("/top-viewed")
    public ResponseDto<Map<String, AdStatsResponseDto>> getTopViewedAds(@RequestBody StatsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        return adStatsService.getTop5AdsByViewCount(user.getId(), request.getPeriod());
    }
}