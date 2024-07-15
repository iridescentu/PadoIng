package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.AdStatsResponseDto;
import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdStats;
import com.sparta.padoing.model.AdHistory;
import com.sparta.padoing.model.VideoAd;
import com.sparta.padoing.repository.AdStatsRepository;
import com.sparta.padoing.repository.AdHistoryRepository;
import com.sparta.padoing.service.AdStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 관련 임포트 추가

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional // 클래스 레벨에 트랜잭션 어노테이션 추가
public class AdStatsServiceImpl implements AdStatsService {

    @Autowired
    private AdStatsRepository adStatsRepository;

    @Autowired
    private AdHistoryRepository adHistoryRepository;

    @Override
    public ResponseDto<Map<String, AdStatsResponseDto>> getTop5AdsByViewCount(Long userId, String period) {
        LocalDate startDate = getStartDate(period);
        LocalDate endDate = getEndDate(period);

        System.out.println("AdStatsServiceImpl.getTop5AdsByViewCount - Start Date: " + startDate + ", End Date: " + endDate);

        generateAdStats(userId, startDate, endDate);

        List<AdStats> adStats = adStatsRepository.findTop5ByVideoAd_Video_User_IdAndDateBetweenOrderByAdViewDesc(userId, startDate, endDate);
        if (adStats.isEmpty()) {
            boolean hasAds = adStatsRepository.existsByVideoAd_Video_User_Id(userId);
            if (hasAds) {
                return new ResponseDto<>("NO_DATA", null, "해당 조회 날짜에 조회할 데이터가 없습니다.");
            } else {
                return new ResponseDto<>("NO_ADS", null, "현재 업로드된 광고가 없어 조회가 불가합니다.");
            }
        }
        Map<String, AdStatsResponseDto> responseDtos = new LinkedHashMap<>();
        for (int i = 0; i < adStats.size(); i++) {
            responseDtos.put("TOP " + (i + 1), AdStatsResponseDto.fromEntity(adStats.get(i)));
        }
        return new ResponseDto<>("SUCCESS", responseDtos, "Top 5 ads by view count retrieved successfully");
    }

    @Override
    public void generateAdStats(Long userId, LocalDate startDate, LocalDate endDate) {
        // 모든 날짜를 초기화
        adStatsRepository.deleteByVideoAd_Video_User_IdAndDateBetween(userId, startDate, endDate);

        // 집계 데이터를 위한 Map 초기화
        Map<Long, AdStats> statsMap = new LinkedHashMap<>();

        List<AdHistory> adHistories = adHistoryRepository.findByCreatedAtBetween(startDate, endDate);
        for (AdHistory adHistory : adHistories) {
            LocalDate date = adHistory.getCreatedAt();
            VideoAd videoAd = adHistory.getVideoAd();
            AdStats adStats = statsMap.computeIfAbsent(videoAd.getId(), k -> AdStats.of(videoAd, 0, date));
            adStats.setAdView(adStats.getAdView() + 1);
        }

        // DB에 저장
        for (AdStats adStats : statsMap.values()) {
            adStatsRepository.save(adStats);
        }
    }

    private LocalDate getStartDate(String period) {
        LocalDate now = LocalDate.now();
        switch (period) {
            case "1일":
                return now;
            case "1주일":
                return now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            case "1달":
                return now.with(TemporalAdjusters.firstDayOfMonth());
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }

    private LocalDate getEndDate(String period) {
        LocalDate now = LocalDate.now();
        switch (period) {
            case "1일":
                return now;
            case "1주일":
                return now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            case "1달":
                return now.with(TemporalAdjusters.lastDayOfMonth());
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }
}