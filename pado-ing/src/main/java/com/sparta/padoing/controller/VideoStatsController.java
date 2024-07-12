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

        // userService를 사용하여 username을 통해 User 객체를 찾음
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        return videoStatsService.getTop5VideosByViewCount(user.getId(), request.getStartDate(), request.getEndDate());
    }

    @PostMapping("/top-played")
    public ResponseDto<Map<String, VideoStatsResponseDto>> getTopPlayedVideos(@RequestBody StatsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // userService를 사용하여 username을 통해 User 객체를 찾음
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        return videoStatsService.getTop5VideosByPlayTime(user.getId(), request.getStartDate(), request.getEndDate());
    }
}