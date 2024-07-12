package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.VideoStmt;

import java.time.LocalDate;
import java.util.List;

public interface VideoStmtService {
    ResponseDto<List<VideoStmt>> getVideoStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
}