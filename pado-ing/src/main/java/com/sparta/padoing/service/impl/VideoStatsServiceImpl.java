package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.VideoStatsResponseDto;
import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.VideoStats;
import com.sparta.padoing.repository.VideoStatsRepository;
import com.sparta.padoing.service.VideoStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoStatsServiceImpl implements VideoStatsService {

    @Autowired
    private VideoStatsRepository videoStatsRepository;

    @Override
    public ResponseDto<Map<String, VideoStatsResponseDto>> getTop5VideosByViewCount(Long userId, LocalDate startDate, LocalDate endDate) {
        System.out.println("Service: getTop5VideosByViewCount called with userId: " + userId + ", startDate: " + startDate + ", endDate: " + endDate);
        List<VideoStats> videoStats = videoStatsRepository.findTop5ByVideo_User_IdAndDateBetweenOrderByVideoViewDesc(userId, startDate, endDate);
        if (videoStats.isEmpty()) {
            return new ResponseDto<>("NO_VIDEOS", null, "현재 업로드된 동영상이 없어 조회가 불가합니다");
        }
        Map<String, VideoStatsResponseDto> responseDtos = new LinkedHashMap<>();
        for (int i = 0; i < videoStats.size(); i++) {
            responseDtos.put("TOP " + (i + 1), VideoStatsResponseDto.fromEntity(videoStats.get(i)));
        }
        return new ResponseDto<>("SUCCESS", responseDtos, "Top 5 videos by view count retrieved successfully");
    }

    @Override
    public ResponseDto<Map<String, VideoStatsResponseDto>> getTop5VideosByPlayTime(Long userId, LocalDate startDate, LocalDate endDate) {
        System.out.println("Service: getTop5VideosByPlayTime called with userId: " + userId + ", startDate: " + startDate + ", endDate: " + endDate);
        List<VideoStats> videoStats = videoStatsRepository.findTop5ByVideo_User_IdAndDateBetweenOrderByPlayTimeDesc(userId, startDate, endDate);
        if (videoStats.isEmpty()) {
            return new ResponseDto<>("NO_VIDEOS", null, "현재 업로드된 동영상이 없어 조회가 불가합니다");
        }
        Map<String, VideoStatsResponseDto> responseDtos = new LinkedHashMap<>();
        for (int i = 0; i < videoStats.size(); i++) {
            responseDtos.put("TOP " + (i + 1), VideoStatsResponseDto.fromEntity(videoStats.get(i)));
        }
        return new ResponseDto<>("SUCCESS", responseDtos, "Top 5 videos by play time retrieved successfully");
    }
}