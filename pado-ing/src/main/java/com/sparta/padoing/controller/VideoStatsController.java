package com.sparta.padoing.controller;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.VideoStats;
import com.sparta.padoing.service.VideoStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stats/videos")
public class VideoStatsController {

    @Autowired
    private VideoStatsService videoStatsService;

    @GetMapping("/top-viewed")
    public ResponseDto<List<VideoStats>> getTopViewedVideos(@RequestParam Long userId,
                                                            @RequestParam LocalDate startDate,
                                                            @RequestParam LocalDate endDate) {
        return videoStatsService.getTop5VideosByViewCount(userId, startDate, endDate);
    }

    @GetMapping("/top-played")
    public ResponseDto<List<VideoStats>> getTopPlayedVideos(@RequestParam Long userId,
                                                            @RequestParam LocalDate startDate,
                                                            @RequestParam LocalDate endDate) {
        return videoStatsService.getTop5VideosByPlayTime(userId, startDate, endDate);
    }
}