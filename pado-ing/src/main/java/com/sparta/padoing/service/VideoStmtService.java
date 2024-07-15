package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;

import java.time.LocalDate;
import java.util.Map;

public interface VideoStmtService {

    ResponseDto<Map<String, Object>> getVideoStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);

    ResponseDto<Map<String, Object>> getVideoRevenue(Long userId, LocalDate startDate, LocalDate endDate);

    void generateVideoStmt(Long userId, LocalDate startDate, LocalDate endDate);
}