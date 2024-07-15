package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.AdStatsResponseDto;
import com.sparta.padoing.dto.response.ResponseDto;

import java.time.LocalDate;
import java.util.Map;

public interface AdStatsService {
    ResponseDto<Map<String, AdStatsResponseDto>> getTop5AdsByViewCount(Long userId, String period);
    void generateAdStats(Long userId, LocalDate startDate, LocalDate endDate);
}