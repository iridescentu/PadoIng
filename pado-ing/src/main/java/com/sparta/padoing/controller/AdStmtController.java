package com.sparta.padoing.controller;

import com.sparta.padoing.dto.request.StatsRequest;
import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.service.AdStmtService;
import com.sparta.padoing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;

@RestController
@RequestMapping("/api/stmts/ads")
public class AdStmtController {

    @Autowired
    private AdStmtService adStmtService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseDto<Map<String, Object>> getAdStmts(@RequestBody StatsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate endDate = getEndDate(request.getPeriod());
        LocalDate startDate = getStartDate(request.getPeriod());

        ResponseDto<Map<String, Object>> response = adStmtService.getAdStmtByUserIdAndDateRange(user.getId(), startDate, endDate);
        response.setStartDate(startDate);
        response.setEndDate(endDate);

        return response;
    }

    @PostMapping("/revenue")
    public ResponseDto<Map<String, Object>> getAdRevenue(@RequestBody StatsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate endDate = getEndDate(request.getPeriod());
        LocalDate startDate = getStartDate(request.getPeriod());

        ResponseDto<Map<String, Object>> response = adStmtService.getAdRevenue(user.getId(), startDate, endDate);
        response.setStartDate(startDate);
        response.setEndDate(endDate);

        return response;
    }

    private LocalDate getStartDate(String period) {
        LocalDate now = LocalDate.now();
        switch (period) {
            case "1일":
                return now;
            case "1주일":
                return now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            case "1달":
                return now.with(TemporalAdjusters.firstDayOfMonth());
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }

    private LocalDate getEndDate(String period) {
        LocalDate now = LocalDate.now();
        switch (period) {
            case "1일":
                return now;
            case "1주일":
                return now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            case "1달":
                return now.with(TemporalAdjusters.lastDayOfMonth());
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }
}