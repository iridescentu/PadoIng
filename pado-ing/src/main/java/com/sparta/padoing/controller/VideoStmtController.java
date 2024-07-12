package com.sparta.padoing.controller;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.VideoStmt;
import com.sparta.padoing.service.VideoStmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stmts/videos")
public class VideoStmtController {

    @Autowired
    private VideoStmtService videoStmtService;

    @GetMapping
    public ResponseDto<List<VideoStmt>> getVideoStmts(@RequestParam Long userId,
                                                      @RequestParam LocalDate startDate,
                                                      @RequestParam LocalDate endDate) {
        return videoStmtService.getVideoStmtByUserIdAndDateRange(userId, startDate, endDate);
    }
}