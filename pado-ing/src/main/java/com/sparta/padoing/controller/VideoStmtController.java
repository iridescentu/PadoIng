package com.sparta.padoing.controller;

import com.sparta.padoing.dto.request.StatsRequest;
import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.VideoStmt;
import com.sparta.padoing.service.UserService;
import com.sparta.padoing.service.VideoStmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stmts/videos")
public class VideoStmtController {

    @Autowired
    private VideoStmtService videoStmtService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseDto<List<VideoStmt>> getVideoStmts(@RequestBody StatsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = calculateStartDate(request.getPeriod(), endDate);
        return videoStmtService.getVideoStmtByUserIdAndDateRange(user.getId(), startDate, endDate);
    }

    @PostMapping("/revenue")
    public ResponseDto<Map<String, Long>> getVideoRevenue(@RequestBody StatsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = calculateStartDate(request.getPeriod(), endDate);
        return videoStmtService.getVideoRevenue(user.getId(), startDate, endDate);
    }

    private LocalDate calculateStartDate(String period, LocalDate endDate) {
        switch (period) {
            case "1일":
                return endDate;
            case "1주일":
                return endDate.with(DayOfWeek.MONDAY);
            case "1달":
                return endDate.withDayOfMonth(1);
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }
}