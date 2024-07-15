//package com.sparta.padoing.service;
//
//import com.sparta.padoing.dto.response.ResponseDto;
//import com.sparta.padoing.model.VideoStmt;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Map;
//
//public interface VideoStmtService {
//    ResponseDto<List<VideoStmt>> getVideoStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
//    ResponseDto<Map<String, Long>> getVideoRevenue(Long userId, LocalDate startDate, LocalDate endDate);
//}

package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.VideoStmt;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface VideoStmtService {
    ResponseDto<List<VideoStmt>> getVideoStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    ResponseDto<Map<String, Long>> getVideoRevenue(Long userId, LocalDate startDate, LocalDate endDate);
    void generateVideoStmt(Long userId, LocalDate startDate, LocalDate endDate);
}