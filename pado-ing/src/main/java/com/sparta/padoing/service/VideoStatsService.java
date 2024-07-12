package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.VideoStatsResponseDto;
import com.sparta.padoing.dto.response.ResponseDto;

import java.time.LocalDate;
import java.util.Map;

public interface VideoStatsService {
    ResponseDto<Map<String, VideoStatsResponseDto>> getTop5VideosByViewCount(Long userId, LocalDate startDate, LocalDate endDate);
    ResponseDto<Map<String, VideoStatsResponseDto>> getTop5VideosByPlayTime(Long userId, LocalDate startDate, LocalDate endDate);
}