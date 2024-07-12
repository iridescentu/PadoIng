package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.VideoStmt;
import com.sparta.padoing.repository.VideoStmtRepository;
import com.sparta.padoing.service.VideoStmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VideoStmtServiceImpl implements VideoStmtService {

    @Autowired
    private VideoStmtRepository videoStmtRepository;

    @Override
    public ResponseDto<List<VideoStmt>> getVideoStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        List<VideoStmt> videoStmts = videoStmtRepository.findByVideo_User_IdAndCreatedAtBetween(userId, startDate, endDate);
        return new ResponseDto<>("SUCCESS", videoStmts, "Video statements retrieved successfully");
    }
}