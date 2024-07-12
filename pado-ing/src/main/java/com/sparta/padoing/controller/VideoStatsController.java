package com.sparta.padoing.controller;

import com.sparta.padoing.dto.request.StatsRequest;
import com.sparta.padoing.dto.response.VideoStatsResponseDto;
import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.service.VideoStatsService;
import com.sparta.padoing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/stats/videos")
public class VideoStatsController {

    @Autowired
    private VideoStatsService videoStatsService;

    @Autowired
    private UserService userService;

    @PostMapping("/top-viewed")
    public ResponseDto<Map<String, VideoStatsResponseDto>> getTopViewedVideos(@RequestBody StatsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = calculateStartDate(request.getPeriod(), endDate);

        System.out.println("Controller: getTopViewedVideos called with User: " + user.getUsername() + ", Period: " + request.getPeriod());

        return videoStatsService.getTop5VideosByViewCount(user.getId(), startDate, endDate);
    }

    @PostMapping("/top-played")
    public ResponseDto<Map<String, VideoStatsResponseDto>> getTopPlayedVideos(@RequestBody StatsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = calculateStartDate(request.getPeriod(), endDate);

        System.out.println("Controller: getTopPlayedVideos called with User: " + user.getUsername() + ", Period: " + request.getPeriod());

        return videoStatsService.getTop5VideosByPlayTime(user.getId(), startDate, endDate);
    }

    private LocalDate calculateStartDate(String period, LocalDate endDate) {
        System.out.println("Controller: calculateStartDate called with Period: " + period + ", EndDate: " + endDate);
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