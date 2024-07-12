package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.VideoStats;
import com.sparta.padoing.repository.VideoStatsRepository;
import com.sparta.padoing.service.VideoStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VideoStatsServiceImpl implements VideoStatsService {

    @Autowired
    private VideoStatsRepository videoStatsRepository;

    @Override
    public ResponseDto<List<VideoStats>> getTop5VideosByViewCount(Long userId, LocalDate startDate, LocalDate endDate) {
        List<VideoStats> videoStats = videoStatsRepository.findTop5ByVideo_User_IdAndCreatedAtBetweenOrderByDailyViewCountDesc(userId, startDate, endDate);
        return new ResponseDto<>("SUCCESS", videoStats, "Top 5 videos by view count retrieved successfully");
    }

    @Override
    public ResponseDto<List<VideoStats>> getTop5VideosByPlayTime(Long userId, LocalDate startDate, LocalDate endDate) {
        List<VideoStats> videoStats = videoStatsRepository.findTop5ByVideo_User_IdAndCreatedAtBetweenOrderByDailyPlayTimeDesc(userId, startDate, endDate);
        return new ResponseDto<>("SUCCESS", videoStats, "Top 5 videos by play time retrieved successfully");
    }
}