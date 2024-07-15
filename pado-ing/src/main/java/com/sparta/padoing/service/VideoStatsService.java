package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.dto.response.VideoStatsResponseDto;

import java.time.LocalDate;
import java.util.Map;

public interface VideoStatsService {
    ResponseDto<Map<String, VideoStatsResponseDto>> getTop5VideosByViewCount(Long userId, String period);
    ResponseDto<Map<String, VideoStatsResponseDto>> getTop5VideosByPlayTime(Long userId, String period);
    void generateVideoStats(Long userId, LocalDate startDate, LocalDate endDate);
}