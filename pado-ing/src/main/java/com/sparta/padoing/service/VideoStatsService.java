package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.VideoStats;

import java.time.LocalDate;
import java.util.List;

public interface VideoStatsService {
    ResponseDto<List<VideoStats>> getTop5VideosByViewCount(Long userId, LocalDate startDate, LocalDate endDate);
    ResponseDto<List<VideoStats>> getTop5VideosByPlayTime(Long userId, LocalDate startDate, LocalDate endDate);
}