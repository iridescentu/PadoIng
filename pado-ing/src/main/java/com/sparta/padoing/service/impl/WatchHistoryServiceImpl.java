package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.Video;
import com.sparta.padoing.model.WatchHistory;
import com.sparta.padoing.repository.VideoRepository;
import com.sparta.padoing.repository.WatchHistoryRepository;
import com.sparta.padoing.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WatchHistoryServiceImpl implements WatchHistoryService {

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public ResponseDto<WatchHistory> updateWatchHistory(User user, Long videoId, int watchDuration, int lastWatchedPosition) {
        Optional<Video> videoOpt = videoRepository.findById(videoId);
        if (videoOpt.isPresent()) {
            Video video = videoOpt.get();
            Optional<WatchHistory> watchHistoryOpt = watchHistoryRepository.findByUserAndVideo(user, video);
            WatchHistory watchHistory;
            if (watchHistoryOpt.isPresent()) {
                watchHistory = watchHistoryOpt.get();
            } else {
                watchHistory = new WatchHistory();
                watchHistory.setUser(user);
                watchHistory.setVideo(video);
            }
            watchHistory.setWatchDuration(watchDuration);
            watchHistory.setLastWatchedPosition(lastWatchedPosition);
            watchHistory.setLastWatchedAt(LocalDateTime.now());
            watchHistoryRepository.save(watchHistory);
            return new ResponseDto<>("SUCCESS", watchHistory, "Watch history updated successfully");
        } else {
            return new ResponseDto<>("NOT_FOUND", null, "Video not found");
        }
    }
}