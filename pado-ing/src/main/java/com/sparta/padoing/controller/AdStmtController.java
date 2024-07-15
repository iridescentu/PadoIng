package com.sparta.padoing.controller;

import com.sparta.padoing.dto.request.StatsRequest;
import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.AdStmt;
import com.sparta.padoing.service.AdStmtService;
import com.sparta.padoing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stmts/ads")
public class AdStmtController {

    @Autowired
    private AdStmtService adStmtService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseDto<List<AdStmt>> getAdStmts(@RequestBody StatsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = calculateStartDate(request.getPeriod(), endDate);

        // 정산 계산 로직 실행
        adStmtService.generateAdStmt(user.getId(), startDate, endDate);

        return adStmtService.getAdStmtByUserIdAndDateRange(user.getId(), startDate, endDate);
    }

    @PostMapping("/revenue")
    public ResponseDto<Map<String, Object>> getAdRevenue(@RequestBody StatsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = calculateStartDate(request.getPeriod(), endDate);

        // 정산 계산 로직 실행
        adStmtService.generateAdStmt(user.getId(), startDate, endDate);

        return adStmtService.getAdRevenue(user.getId(), startDate, endDate);
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