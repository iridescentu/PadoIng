package com.sparta.padoing.controller;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdStmt;
import com.sparta.padoing.service.AdStmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stmts/ads")
public class AdStmtController {

    @Autowired
    private AdStmtService adStmtService;

    @GetMapping
    public ResponseDto<List<AdStmt>> getAdStmts(@RequestParam Long userId,
                                                @RequestParam LocalDate startDate,
                                                @RequestParam LocalDate endDate) {
        return adStmtService.findByUserIdAndCreatedAtBetween(userId, startDate, endDate);
    }
}