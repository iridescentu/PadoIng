package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.AdStatsResponseDto;
import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdStats;
import com.sparta.padoing.repository.AdStatsRepository;
import com.sparta.padoing.service.AdStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdStatsServiceImpl implements AdStatsService {

    @Autowired
    private AdStatsRepository adStatsRepository;

    @Override
    public ResponseDto<Map<String, AdStatsResponseDto>> getTop5AdsByViewCount(Long userId, LocalDate startDate, LocalDate endDate) {
        List<AdStats> adStats = adStatsRepository.findTop5ByVideoAd_Video_User_IdAndDateBetweenOrderByAdViewDesc(userId, startDate, endDate);
        if (adStats.isEmpty()) {
            return new ResponseDto<>("NO_VIDEOS", null, "현재 업로드된 동영상이 없어 조회가 불가합니다");
        }
        Map<String, AdStatsResponseDto> responseDtos = new LinkedHashMap<>();
        for (int i = 0; i < adStats.size(); i++) {
            responseDtos.put("TOP " + (i + 1), AdStatsResponseDto.fromEntity(adStats.get(i)));
        }
        return new ResponseDto<>("SUCCESS", responseDtos, "Top 5 ads by view count retrieved successfully");
    }

    @Override
    public ResponseDto<Map<String, AdStatsResponseDto>> getTop5AdsByPlayTime(Long userId, LocalDate startDate, LocalDate endDate) {
        List<AdStats> adStats = adStatsRepository.findTop5ByVideoAd_Video_User_IdAndDateBetweenOrderByAdViewDesc(userId, startDate, endDate);
        if (adStats.isEmpty()) {
            return new ResponseDto<>("NO_VIDEOS", null, "현재 업로드된 동영상이 없어 조회가 불가합니다");
        }
        Map<String, AdStatsResponseDto> responseDtos = new LinkedHashMap<>();
        for (int i = 0; i < adStats.size(); i++) {
            responseDtos.put("TOP " + (i + 1), AdStatsResponseDto.fromEntity(adStats.get(i)));
        }
        return new ResponseDto<>("SUCCESS", responseDtos, "Top 5 ads by play time retrieved successfully");
    }
}