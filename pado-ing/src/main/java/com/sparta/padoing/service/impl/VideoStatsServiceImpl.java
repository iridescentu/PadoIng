package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.dto.response.VideoStatsResponseDto;
import com.sparta.padoing.model.VideoStats;
import com.sparta.padoing.model.WatchHistory;
import com.sparta.padoing.repository.VideoStatsRepository;
import com.sparta.padoing.repository.WatchHistoryRepository;
import com.sparta.padoing.service.VideoStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VideoStatsServiceImpl implements VideoStatsService {

    @Autowired
    private VideoStatsRepository videoStatsRepository;

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    @Override
    public ResponseDto<Map<String, VideoStatsResponseDto>> getTop5VideosByViewCount(Long userId, String period) {
        LocalDate startDate = getStartDate(period);
        LocalDate endDate = getEndDate(period);

        // 통계 생성 로직 호출
        generateVideoStats(userId, startDate, endDate);

        List<VideoStats> videoStats = videoStatsRepository.findTop5ByVideo_User_IdAndDateBetweenOrderByVideoViewDesc(userId, startDate, endDate);
        if (videoStats.isEmpty()) {
            boolean hasVideos = videoStatsRepository.existsByVideo_User_Id(userId);
            if (hasVideos) {
                return new ResponseDto<>("NO_DATA", null, "해당 조회 날짜에 조회할 데이터가 없습니다.");
            } else {
                return new ResponseDto<>("NO_VIDEOS", null, "현재 업로드된 동영상이 없어 조회가 불가합니다.");
            }
        }
        Map<String, VideoStatsResponseDto> responseDtos = new LinkedHashMap<>();
        for (int i = 0; i < videoStats.size(); i++) {
            responseDtos.put("TOP " + (i + 1), VideoStatsResponseDto.fromEntity(videoStats.get(i)));
        }
        return new ResponseDto<>("SUCCESS", responseDtos, "Top 5 videos by view count retrieved successfully");
    }

    @Override
    public ResponseDto<Map<String, VideoStatsResponseDto>> getTop5VideosByPlayTime(Long userId, String period) {
        LocalDate startDate = getStartDate(period);
        LocalDate endDate = getEndDate(period);

        // 통계 생성 로직 호출
        generateVideoStats(userId, startDate, endDate);

        List<VideoStats> videoStats = videoStatsRepository.findTop5ByVideo_User_IdAndDateBetweenOrderByPlayTimeDesc(userId, startDate, endDate);
        if (videoStats.isEmpty()) {
            boolean hasVideos = videoStatsRepository.existsByVideo_User_Id(userId);
            if (hasVideos) {
                return new ResponseDto<>("NO_DATA", null, "해당 조회 날짜에 조회할 데이터가 없습니다.");
            } else {
                return new ResponseDto<>("NO_VIDEOS", null, "현재 업로드된 동영상이 없어 조회가 불가합니다.");
            }
        }
        Map<String, VideoStatsResponseDto> responseDtos = new LinkedHashMap<>();
        for (int i = 0; i < videoStats.size(); i++) {
            responseDtos.put("TOP " + (i + 1), VideoStatsResponseDto.fromEntity(videoStats.get(i)));
        }
        return new ResponseDto<>("SUCCESS", responseDtos, "Top 5 videos by play time retrieved successfully");
    }

    @Override
    public void generateVideoStats(Long userId, LocalDate startDate, LocalDate endDate) {
        List<WatchHistory> watchHistories = watchHistoryRepository.findByCreatedAtBetween(startDate, endDate);
        for (WatchHistory watchHistory : watchHistories) {
            LocalDate date = watchHistory.getCreatedAt();
            Long videoId = watchHistory.getVideo().getId();
            Optional<VideoStats> videoStatsOpt = videoStatsRepository.findByVideo_IdAndDate(videoId, date);
            VideoStats videoStats = videoStatsOpt.orElseGet(() -> VideoStats.of(watchHistory.getVideo(), 0, 0L));
            videoStats.setVideoView(videoStats.getVideoView() + 1);
            videoStats.setPlayTime(videoStats.getPlayTime() + watchHistory.getWatchDuration());
            videoStatsRepository.save(videoStats);
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