package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdStmt;

import java.time.LocalDate;
import java.util.List;

public interface AdStmtService {
    ResponseDto<List<AdStmt>> findByUserIdAndCreatedAtBetween(Long userId, LocalDate startDate, LocalDate endDate);
}