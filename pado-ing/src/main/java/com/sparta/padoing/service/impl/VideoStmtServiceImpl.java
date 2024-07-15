package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.VideoStmt;
import com.sparta.padoing.model.VideoStats;
import com.sparta.padoing.repository.VideoStmtRepository;
import com.sparta.padoing.repository.VideoStatsRepository;
import com.sparta.padoing.service.VideoStmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoStmtServiceImpl implements VideoStmtService {

    @Autowired
    private VideoStmtRepository videoStmtRepository;

    @Autowired
    private VideoStatsRepository videoStatsRepository;

    @Override
    public ResponseDto<List<VideoStmt>> getVideoStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        List<VideoStmt> videoStmts = videoStmtRepository.findByVideo_User_IdAndCreatedAtBetween(userId, startDate, endDate);
        if (videoStmts.isEmpty()) {
            return new ResponseDto<>("NO_DATA", null, "조회할 데이터가 없습니다.");
        }
        return new ResponseDto<>("SUCCESS", videoStmts, "Video statements retrieved successfully");
    }

    @Override
    public ResponseDto<Map<String, Long>> getVideoRevenue(Long userId, LocalDate startDate, LocalDate endDate) {
        List<VideoStmt> videoStmts = videoStmtRepository.findByVideo_User_IdAndCreatedAtBetween(userId, startDate, endDate);
        if (videoStmts.isEmpty()) {
            return new ResponseDto<>("NO_DATA", null, "해당 조회 날짜에 조회할 데이터가 없습니다.");
        }

        Map<String, Long> videoRevenueMap = new HashMap<>();
        for (VideoStmt videoStmt : videoStmts) {
            long revenue = calculateVideoRevenue(videoStmt);
            videoRevenueMap.put("Video " + videoStmt.getVideo().getId(), revenue);
        }

        return new ResponseDto<>("SUCCESS", videoRevenueMap, "Video revenue calculated successfully");
    }

    private long calculateVideoRevenue(VideoStmt videoStmt) {
        long viewCount = getVideoView(videoStmt.getVideo().getId(), videoStmt.getCreatedAt());
        double rate = 0;

        if (viewCount < 100000) {
            rate = 1.0;
        } else if (viewCount < 500000) {
            rate = 1.1;
        } else if (viewCount < 1000000) {
            rate = 1.3;
        } else {
            rate = 1.5;
        }

        return (long) (viewCount * rate);
    }

    private long getVideoView(Long videoId, LocalDate date) {
        return videoStatsRepository.findByVideo_IdAndDate(videoId, date)
                .map(VideoStats::getVideoView)
                .orElse(0);
    }
}