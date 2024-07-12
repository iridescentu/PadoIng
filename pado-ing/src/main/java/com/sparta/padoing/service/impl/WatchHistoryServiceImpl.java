//package com.sparta.padoing.service.impl;
//
//import com.sparta.padoing.dto.response.ResponseDto;
//import com.sparta.padoing.dto.response.WatchHistoryResponseDto;
//import com.sparta.padoing.model.*;
//import com.sparta.padoing.repository.AdStatsRepository;
//import com.sparta.padoing.repository.VideoAdRepository;
//import com.sparta.padoing.repository.VideoRepository;
//import com.sparta.padoing.repository.WatchHistoryRepository;
//import com.sparta.padoing.service.AdHistoryService;
//import com.sparta.padoing.service.WatchHistoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class WatchHistoryServiceImpl implements WatchHistoryService {
//
//    @Autowired
//    private WatchHistoryRepository watchHistoryRepository;
//
//    @Autowired
//    private VideoRepository videoRepository;
//
//    @Autowired
//    private VideoAdRepository videoAdRepository;
//
//    @Autowired
//    private AdHistoryService adHistoryService;
//
//    @Autowired
//    private AdStatsRepository adStatsRepository;
//
//    @Override
//    public ResponseDto<WatchHistoryResponseDto> updateWatchHistory(User user, Long videoId, int watchDuration, int lastWatchedPosition) {
//        Optional<Video> videoOpt = videoRepository.findById(videoId);
//        if (videoOpt.isPresent()) {
//            Video video = videoOpt.get();
//
//            // 비디오가 삭제되었는지 확인
//            if (video.isDeleted()) {
//                return new ResponseDto<>("ERROR", null, "삭제된 동영상은 재생할 수 없습니다.");
//            }
//
//            // lastWatchedPosition이 동영상의 재생 시간을 초과하는지 확인
//            if (lastWatchedPosition > video.getDuration()) {
//                return new ResponseDto<>("ERROR", null, "동영상의 재생 시간을 넘길 수 없습니다.");
//            }
//
//            WatchHistory watchHistory = new WatchHistory();
//            watchHistory.setUser(user);
//            watchHistory.setVideo(video);
//            watchHistory.setWatchDuration(watchDuration);
//            watchHistory.setLastWatchedPosition(lastWatchedPosition);
//            watchHistory.setCreatedAt(LocalDate.now());
//            watchHistoryRepository.save(watchHistory);
//
//            incrementAdViews(video, user, lastWatchedPosition);
//
//            WatchHistoryResponseDto watchHistoryResponseDto = new WatchHistoryResponseDto(watchHistory);
//            return new ResponseDto<>("SUCCESS", watchHistoryResponseDto, "Watch history updated successfully");
//        } else {
//            return new ResponseDto<>("NOT_FOUND", null, "Video not found");
//        }
//    }
//
//    private void incrementAdViews(Video video, User currentUser, int lastWatchedPosition) {
//        // 광고 시청 횟수 증가
//        List<VideoAd> videoAds = videoAdRepository.findByVideo(video);
//        int adInterval = 5 * 60; // 5분 간격 (300초 간격)
//
//        for (int i = 0; i < videoAds.size(); i++) {
//            VideoAd videoAd = videoAds.get(i);
//            int adPosition = adInterval * (i + 1); // 각 광고 위치를 5분 간격으로 설정
//            System.out.println("Ad Position: " + adPosition + ", Last Watched Position: " + lastWatchedPosition);
//
//            // 광고가 삽입된 위치가 lastWatchedPosition보다 작거나 같으면 시청된 것으로 간주
//            if (lastWatchedPosition >= adPosition) {
//                System.out.println("Ad viewed. VideoAd ID: " + videoAd.getId());
//                // 현재 사용자가 비디오를 올린 사용자가 아닌 경우
//                if (!video.getUser().getId().equals(currentUser.getId())) {
//                    videoAd.setViews(videoAd.getViews() + 1);
//                    videoAdRepository.save(videoAd);
//                    System.out.println("Ad view count incremented. New view count: " + videoAd.getViews());
//
//                    // AdHistory에 추가
//                    adHistoryService.createAdHistory(currentUser, videoAd);
//
//                    // AdStats 업데이트
//                    updateAdStats(videoAd, 1);
//                }
//            }
//        }
//    }
//
//    private void updateAdStats(VideoAd videoAd, int viewCountIncrement) {
//        LocalDate today = LocalDate.now();
//        Optional<AdStats> adStatsOpt = adStatsRepository.findByVideoAd_IdAndDate(videoAd.getId(), today);
//
//        AdStats adStats = adStatsOpt.orElseGet(() -> AdStats.of(videoAd, 0));
//
//        adStats.setAdView(adStats.getAdView() + viewCountIncrement);
//
//        adStatsRepository.save(adStats);
//    }
//}

package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.dto.response.WatchHistoryResponseDto;
import com.sparta.padoing.model.*;
import com.sparta.padoing.repository.AdStatsRepository;
import com.sparta.padoing.repository.VideoAdRepository;
import com.sparta.padoing.repository.VideoRepository;
import com.sparta.padoing.repository.VideoStatsRepository;
import com.sparta.padoing.repository.WatchHistoryRepository;
import com.sparta.padoing.service.AdHistoryService;
import com.sparta.padoing.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Autowired
    private VideoStatsRepository videoStatsRepository;

    @Autowired
    private AdHistoryService adHistoryService;

    @Autowired
    private AdStatsRepository adStatsRepository;

    @Override
    public ResponseDto<WatchHistoryResponseDto> updateWatchHistory(User user, Long videoId, int watchDuration, int lastWatchedPosition) {
        Optional<Video> videoOpt = videoRepository.findById(videoId);
        if (videoOpt.isPresent()) {
            Video video = videoOpt.get();

            // 비디오가 삭제되었는지 확인
            if (video.isDeleted()) {
                return new ResponseDto<>("ERROR", null, "삭제된 동영상은 재생할 수 없습니다.");
            }

            // lastWatchedPosition이 동영상의 재생 시간을 초과하는지 확인
            if (lastWatchedPosition > video.getDuration()) {
                return new ResponseDto<>("ERROR", null, "동영상의 재생 시간을 넘길 수 없습니다.");
            }

            WatchHistory watchHistory = new WatchHistory();
            watchHistory.setUser(user);
            watchHistory.setVideo(video);
            watchHistory.setWatchDuration(watchDuration);
            watchHistory.setLastWatchedPosition(lastWatchedPosition);
            watchHistory.setCreatedAt(LocalDate.now());
            watchHistoryRepository.save(watchHistory);

            // 본인이 올린 동영상이 아닌 경우에만 VideoStats 업데이트
            if (!video.getUser().getId().equals(user.getId())) {
                updateVideoStats(video, 1, watchDuration);
            }

            incrementAdViews(video, user, lastWatchedPosition);

            WatchHistoryResponseDto watchHistoryResponseDto = new WatchHistoryResponseDto(watchHistory);
            return new ResponseDto<>("SUCCESS", watchHistoryResponseDto, "Watch history updated successfully");
        } else {
            return new ResponseDto<>("NOT_FOUND", null, "Video not found");
        }
    }

    private void incrementAdViews(Video video, User currentUser, int lastWatchedPosition) {
        // 광고 시청 횟수 증가
        List<VideoAd> videoAds = videoAdRepository.findByVideo(video);
        int adInterval = 5 * 60; // 5분 간격 (300초 간격)

        for (int i = 0; i < videoAds.size(); i++) {
            VideoAd videoAd = videoAds.get(i);
            int adPosition = adInterval * (i + 1); // 각 광고 위치를 5분 간격으로 설정
            System.out.println("Ad Position: " + adPosition + ", Last Watched Position: " + lastWatchedPosition);

            // 광고가 삽입된 위치가 lastWatchedPosition보다 작거나 같으면 시청된 것으로 간주
            if (lastWatchedPosition >= adPosition) {
                System.out.println("Ad viewed. VideoAd ID: " + videoAd.getId());
                // 현재 사용자가 비디오를 올린 사용자가 아닌 경우
                if (!video.getUser().getId().equals(currentUser.getId())) {
                    videoAd.setViews(videoAd.getViews() + 1);
                    videoAdRepository.save(videoAd);
                    System.out.println("Ad view count incremented. New view count: " + videoAd.getViews());

                    // AdHistory에 추가
                    adHistoryService.createAdHistory(currentUser, videoAd);

                    // AdStats 업데이트
                    updateAdStats(videoAd, 1);
                }
            }
        }
    }

    private void updateAdStats(VideoAd videoAd, int viewCountIncrement) {
        LocalDate today = LocalDate.now();
        Optional<AdStats> adStatsOpt = adStatsRepository.findByVideoAd_IdAndDate(videoAd.getId(), today);

        AdStats adStats = adStatsOpt.orElseGet(() -> AdStats.of(videoAd, 0));

        adStats.setAdView(adStats.getAdView() + viewCountIncrement);

        adStatsRepository.save(adStats);
    }

    private void updateVideoStats(Video video, int viewCountIncrement, long playTimeIncrement) {
        LocalDate today = LocalDate.now();
        Optional<VideoStats> videoStatsOpt = videoStatsRepository.findByVideo_IdAndDate(video.getId(), today);

        VideoStats videoStats = videoStatsOpt.orElseGet(() -> VideoStats.of(video, 0, 0L));

        videoStats.setVideoView(videoStats.getVideoView() + viewCountIncrement);
        videoStats.setPlayTime(videoStats.getPlayTime() + playTimeIncrement);

        videoStatsRepository.save(videoStats);
    }
}