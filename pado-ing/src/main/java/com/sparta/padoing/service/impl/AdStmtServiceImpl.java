package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdStmt;
import com.sparta.padoing.model.AdStats;
import com.sparta.padoing.model.VideoAd;
import com.sparta.padoing.repository.AdStmtRepository;
import com.sparta.padoing.repository.AdStatsRepository;
import com.sparta.padoing.repository.VideoAdRepository;
import com.sparta.padoing.service.AdStmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdStmtServiceImpl implements AdStmtService {

    @Autowired
    private AdStmtRepository adStmtRepository;

    @Autowired
    private AdStatsRepository adStatsRepository;

    @Autowired
    private VideoAdRepository videoAdRepository;

    @Override
    public ResponseDto<List<AdStmt>> getAdStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        generateAdStmt(userId, startDate, endDate);

        List<AdStmt> adStmts = adStmtRepository.findByVideoAd_Video_User_IdAndDateBetween(userId, startDate, endDate);
        if (adStmts.isEmpty()) {
            return new ResponseDto<>("NO_DATA", null, "조회할 데이터가 없습니다.");
        }
        return new ResponseDto<>("SUCCESS", adStmts, "Ad statements retrieved successfully");
    }

    @Override
    public ResponseDto<Map<String, Long>> getAdRevenue(Long userId, LocalDate startDate, LocalDate endDate) {
        generateAdStmt(userId, startDate, endDate);

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
        long rate;

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

    @Override
    public void generateAdStmt(Long userId, LocalDate startDate, LocalDate endDate) {
        List<VideoAd> videoAds = videoAdRepository.findByVideo_User_Id(userId);
        for (VideoAd videoAd : videoAds) {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                List<AdStats> adStatsList = adStatsRepository.findByVideoAd_IdAndDateBetween(videoAd.getId(), date, date);
                long totalViewCount = 0;
                for (AdStats adStats : adStatsList) {
                    totalViewCount += adStats.getAdView();
                }
                int adStmtValue = (int) totalViewCount;
                AdStmt stmt = new AdStmt(videoAd, date, adStmtValue, adStmtValue);
                adStmtRepository.save(stmt);
            }
        }
    }
}