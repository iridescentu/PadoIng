package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdStmt;
import com.sparta.padoing.model.Video;
import com.sparta.padoing.model.VideoStmt;
import com.sparta.padoing.model.VideoStats;
import com.sparta.padoing.repository.AdStmtRepository;
import com.sparta.padoing.repository.VideoRepository;
import com.sparta.padoing.repository.VideoStmtRepository;
import com.sparta.padoing.repository.VideoStatsRepository;
import com.sparta.padoing.service.VideoStmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
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

    @Autowired
    private AdStmtRepository adStmtRepository;

    @Override
    public ResponseDto<Map<String, Object>> getVideoStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        generateVideoStmt(userId, startDate, endDate);

        List<VideoStmt> videoStmts = videoStmtRepository.findByVideo_User_IdAndDateBetween(userId, startDate, endDate);
        List<AdStmt> adStmts = adStmtRepository.findByVideoAd_Video_User_IdAndDateBetween(userId, startDate, endDate);

        if (videoStmts.isEmpty() && adStmts.isEmpty()) {
            return new ResponseDto<>("NO_DATA", null, "조회할 데이터가 없습니다.", startDate, endDate);
        }

        Map<String, Object> responseMap = new LinkedHashMap<>();
        long totalRevenue = 0;

        for (VideoStmt videoStmt : videoStmts) {
            long videoRevenue = videoStmt.getVideoStmt();
            long adRevenue = adStmts.stream()
                    .filter(adStmt -> adStmt.getVideoAd().getVideo().getId().equals(videoStmt.getVideo().getId()))
                    .mapToLong(this::calculateAdRevenue)
                    .sum();
            totalRevenue += videoRevenue + adRevenue;

            Map<String, Object> videoDetails = new LinkedHashMap<>();
            videoDetails.put("videoTitle", videoStmt.getVideo().getTitle());
            videoDetails.put("videoStmt", videoRevenue);
            videoDetails.put("adStmt", adRevenue);

            responseMap.put("Video " + videoStmt.getVideo().getId(), videoDetails);
        }

        responseMap.put("totalRevenue", totalRevenue);
        return new ResponseDto<>("SUCCESS", responseMap, "Video statements retrieved successfully", startDate, endDate);
    }

    @Override
    public ResponseDto<Map<String, Object>> getVideoRevenue(Long userId, LocalDate startDate, LocalDate endDate) {
        generateVideoStmt(userId, startDate, endDate);

        List<VideoStmt> videoStmts = videoStmtRepository.findByVideo_User_IdAndDateBetween(userId, startDate, endDate);
        if (videoStmts.isEmpty()) {
            return new ResponseDto<>("NO_DATA", null, "해당 조회 날짜에 조회할 데이터가 없습니다.", startDate, endDate);
        }

        Map<String, Object> responseMap = new LinkedHashMap<>();
        long totalRevenue = 0;

        for (VideoStmt videoStmt : videoStmts) {
            long videoRevenue = videoStmt.getVideoStmt();
            totalRevenue += videoRevenue;

            Map<String, Object> videoDetails = new LinkedHashMap<>();
            videoDetails.put("videoTitle", videoStmt.getVideo().getTitle());
            videoDetails.put("videoRevenue", videoRevenue);
            responseMap.put("Video " + videoStmt.getVideo().getId(), videoDetails);
        }

        responseMap.put("totalRevenue", totalRevenue);
        return new ResponseDto<>("SUCCESS", responseMap, "Video revenue calculated successfully", startDate, endDate);
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
            int adStmtValue = 0; // adStmt 값을 기본값으로 초기화
            VideoStmt stmt = new VideoStmt(video, endDate, videoStmtValue, adStmtValue); // 새로운 생성자를 사용합니다.
            videoStmtRepository.save(stmt);
        }
    }
}