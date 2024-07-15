package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdStmt;
import com.sparta.padoing.repository.AdStmtRepository;
import com.sparta.padoing.service.AdStmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdStmtServiceImpl implements AdStmtService {

    @Autowired
    private AdStmtRepository adStmtRepository;

    @Override
    public ResponseDto<List<AdStmt>> getAdStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        List<AdStmt> adStmts = adStmtRepository.findByVideoAd_Video_User_IdAndDateBetween(userId, startDate, endDate);
        if (adStmts.isEmpty()) {
            return new ResponseDto<>("NO_DATA", null, "조회할 데이터가 없습니다.");
        }
        return new ResponseDto<>("SUCCESS", adStmts, "Ad statements retrieved successfully");
    }

    @Override
    public ResponseDto<Map<String, Long>> getAdRevenue(Long userId, LocalDate startDate, LocalDate endDate) {
        List<AdStmt> adStmts = adStmtRepository.findByVideoAd_Video_User_IdAndDateBetween(userId, startDate, endDate);
        if (adStmts.isEmpty()) {
            return new ResponseDto<>("NO_DATA", null, "해당 조회 날짜에 조회할 데이터가 없습니다.");
        }

        Map<String, Long> adRevenueMap = new HashMap<>();
        for (AdStmt adStmt : adStmts) {
            long revenue = calculateAdRevenue(adStmt);
            adRevenueMap.put("Ad " + adStmt.getVideoAd().getId(), revenue);
        }

        return new ResponseDto<>("SUCCESS", adRevenueMap, "Ad revenue calculated successfully");
    }

    private long calculateAdRevenue(AdStmt adStmt) {
        long viewCount = adStmt.getAdView();
        long rate = 0;

        if (viewCount < 100000) {
            rate = 10;
        } else if (viewCount < 500000) {
            rate = 12;
        } else if (viewCount < 1000000) {
            rate = 15;
        } else {
            rate = 20;
        }

        return (viewCount * rate) / 100;
    }
}