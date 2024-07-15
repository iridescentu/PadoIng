package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdStmt;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AdStmtService {

    ResponseDto<List<AdStmt>> getAdStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);

    ResponseDto<Map<String, Object>> getAdRevenue(Long userId, LocalDate startDate, LocalDate endDate);

    void generateAdStmt(Long userId, LocalDate startDate, LocalDate endDate);
}