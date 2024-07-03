package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.Video;
import com.sparta.padoing.model.VideoAd;
import com.sparta.padoing.model.WatchHistory;
import com.sparta.padoing.repository.VideoAdRepository;
import com.sparta.padoing.repository.VideoRepository;
import com.sparta.padoing.repository.WatchHistoryRepository;
import com.sparta.padoing.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WatchHistoryServiceImpl implements WatchHistoryService {

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoAdRepository videoAdRepository;

    @Override
    public ResponseDto<WatchHistory> updateWatchHistory(User user, Long videoId, int watchDuration, int lastWatchedPosition) {
        // 시청 기록 업데이트
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

            // 광고 시청 횟수 증가 로직 추가
            incrementAdViews(video, user, lastWatchedPosition);

            return new ResponseDto<>("SUCCESS", watchHistory, "Watch history updated successfully");
        } else {
            return new ResponseDto<>("NOT_FOUND", null, "Video not found");
        }
    }

    private void incrementAdViews(Video video, User currentUser, int lastWatchedPosition) {
        // 광고 시청 횟수 증가
        List<VideoAd> videoAds = videoAdRepository.findByVideo(video);
        int adInterval = 5 * 60; // 5분 간격

        for (VideoAd videoAd : videoAds) {
            // 광고가 삽입된 위치 계산
            int adPosition = adInterval * videoAd.getId().intValue();

            // 광고가 삽입된 위치가 lastWatchedPosition보다 작거나 같으면 시청된 것으로 간주
            if (lastWatchedPosition >= adPosition) {
                // 현재 사용자가 비디오를 올린 사용자가 아닌 경우
                if (!video.getUser().getId().equals(currentUser.getId())) {
                    videoAd.setViews(videoAd.getViews() + 1);
                    videoAdRepository.save(videoAd);
                }
            }
        }
    }
}