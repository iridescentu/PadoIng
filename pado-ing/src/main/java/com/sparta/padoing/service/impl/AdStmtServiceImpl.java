package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdStmt;
import com.sparta.padoing.repository.AdStmtRepository;
import com.sparta.padoing.service.AdStmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdStmtServiceImpl implements AdStmtService {

    @Autowired
    private AdStmtRepository adStmtRepository;

    @Override
    public ResponseDto<List<AdStmt>> findByUserIdAndCreatedAtBetween(Long userId, LocalDate startDate, LocalDate endDate) {
        List<AdStmt> adStmts = adStmtRepository.findByUser_IdAndCreatedAtBetween(userId, startDate, endDate);
        return new ResponseDto<>("SUCCESS", adStmts, "Ad statements retrieved successfully");
    }
}