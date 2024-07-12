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

import java.time.LocalDate;
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

        // userService를 사용하여 username을 통해 User 객체를 찾음
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = calculateStartDate(request.getPeriod(), endDate);

        return adStatsService.getTop5AdsByViewCount(user.getId(), startDate, endDate);
    }

    @PostMapping("/top-played")
    public ResponseDto<Map<String, AdStatsResponseDto>> getTopPlayedAds(@RequestBody StatsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // userService를 사용하여 username을 통해 User 객체를 찾음
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = calculateStartDate(request.getPeriod(), endDate);

        return adStatsService.getTop5AdsByPlayTime(user.getId(), startDate, endDate);
    }

    private LocalDate calculateStartDate(String period, LocalDate endDate) {
        switch (period) {
            case "1일":
                return endDate.minusDays(1);
            case "1주일":
                return endDate.minusWeeks(1);
            case "1달":
                return endDate.minusMonths(1);
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }
}