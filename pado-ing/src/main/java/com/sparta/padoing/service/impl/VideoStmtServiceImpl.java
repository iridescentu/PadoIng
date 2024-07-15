package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.Video;
import com.sparta.padoing.model.VideoStmt;
import com.sparta.padoing.model.VideoStats;
import com.sparta.padoing.repository.VideoRepository;
import com.sparta.padoing.repository.VideoStmtRepository;
import com.sparta.padoing.repository.VideoStatsRepository;
import com.sparta.padoing.service.VideoStmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VideoStmtServiceImpl implements VideoStmtService {

    @Autowired
    private VideoStmtRepository videoStmtRepository;

    @Autowired
    private VideoStatsRepository videoStatsRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public ResponseDto<List<VideoStmt>> getVideoStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        // 정산 계산 로직 실행
        generateVideoStmt(userId, startDate, endDate);

        List<VideoStmt> videoStmts = videoStmtRepository.findByVideo_User_IdAndDateBetween(userId, startDate, endDate);
        if (videoStmts.isEmpty()) {
            return new ResponseDto<>("NO_DATA", null, "조회할 데이터가 없습니다.");
        }
        return new ResponseDto<>("SUCCESS", videoStmts, "Video statements retrieved successfully");
    }

    @Override
    public ResponseDto<Map<String, Long>> getVideoRevenue(Long userId, LocalDate startDate, LocalDate endDate) {
        // 정산 계산 로직 실행
        generateVideoStmt(userId, startDate, endDate);

        List<VideoStmt> videoStmts = videoStmtRepository.findByVideo_User_IdAndDateBetween(userId, startDate, endDate);
        if (videoStmts.isEmpty()) {
            return new ResponseDto<>("NO_DATA", null, "해당 조회 날짜에 조회할 데이터가 없습니다.");
        }

        Map<String, Long> videoRevenueMap = new HashMap<>();
        for (VideoStmt videoStmt : videoStmts) {
            long revenue = videoStmt.getVideoStmt();
            videoRevenueMap.put("Video " + videoStmt.getVideo().getId(), revenue);
        }

        return new ResponseDto<>("SUCCESS", videoRevenueMap, "Video revenue calculated successfully");
    }

    private long calculateVideoRevenue(long viewCount) {
        double rate;

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

    private long getVideoView(Long videoId, LocalDate startDate, LocalDate endDate) {
        return videoStatsRepository.findByVideo_IdAndDateBetween(videoId, startDate, endDate)
                .stream()
                .mapToLong(VideoStats::getVideoView)
                .sum();
    }

    @Override
    public void generateVideoStmt(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Video> videos = videoRepository.findByUser_Id(userId);
        for (Video video : videos) {
            long totalViewCount = getVideoView(video.getId(), startDate, endDate);
            int videoStmtValue = (int) calculateVideoRevenue(totalViewCount);
            VideoStmt stmt = new VideoStmt(video, endDate, videoStmtValue);
            videoStmtRepository.save(stmt);
        }
    }
}