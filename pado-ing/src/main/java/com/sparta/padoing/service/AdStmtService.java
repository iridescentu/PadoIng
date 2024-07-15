package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;

import java.time.LocalDate;
import java.util.Map;

public interface AdStmtService {

    ResponseDto<Map<String, Object>> getAdStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);

    ResponseDto<Map<String, Object>> getAdRevenue(Long userId, LocalDate startDate, LocalDate endDate);

    void generateAdStmt(Long userId, LocalDate startDate, LocalDate endDate);
}