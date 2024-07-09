package com.sparta.padoing.controller;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.dto.response.VideoResponseDto;
import com.sparta.padoing.dto.response.WatchHistoryResponseDto;
import com.sparta.padoing.model.*;
import com.sparta.padoing.service.UserService;
import com.sparta.padoing.service.VideoAdService;
import com.sparta.padoing.service.VideoService;
import com.sparta.padoing.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @Autowired
    private WatchHistoryService watchHistoryService; // 시청 기록 서비스 추가

    @Autowired
    private VideoAdService videoAdService;

    // 모든 동영상 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<VideoResponseDto>>> getAllVideos() {
        ResponseDto<List<VideoResponseDto>> response = videoService.findAll();
        return ResponseEntity.ok(response);
    }

    // 동영상 ID로 동영상 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<VideoResponseDto>> getVideoById(@PathVariable Long id) {
        ResponseDto<Optional<VideoResponseDto>> response = videoService.findById(id);
        if (response.getResultCode().equals("SUCCESS") && response.getData().isPresent()) {
            return ResponseEntity.ok(new ResponseDto<>("SUCCESS", response.getData().get(), "Video retrieved successfully"));
        } else {
            return ResponseEntity.status(404).body(new ResponseDto<>("ERROR", null, "Video not found"));
        }
    }

    // 활성 상태의 동영상 조회
    @GetMapping("/active")
    public ResponseEntity<ResponseDto<List<VideoResponseDto>>> getActiveVideos() {
        ResponseDto<List<VideoResponseDto>> response = videoService.findActiveVideos();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseDto<VideoResponseDto>> createVideo(@RequestBody Video video) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not authenticated"));
        }

        String username = authentication.getName();
        System.out.println("Authenticated username: " + username);

        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            video.setUser(user);
            video.setUploadDate(LocalDate.now());
            video.setActive(true); // 기본값 설정

            // 동영상을 업로드하면 UPLOADER 역할 부여
            user.addRole(Role.UPLOADER);

            // UPLOADER 역할 부여 뒤 User 정보 업데이트
            userService.saveOrUpdateUser(user);

            Video savedVideo = videoService.save(video).getData();

            // 광고 목록을 가져와 VideoResponseDto에 포함
            List<VideoAd> videoAds = videoService.findVideoAdsByVideo(savedVideo);
            List<Ad> ads = videoAds.stream().map(VideoAd::getAd).collect(Collectors.toList());

            VideoResponseDto videoResponseDto = new VideoResponseDto(savedVideo, ads);
            return ResponseEntity.status(201).body(new ResponseDto<>("SUCCESS", videoResponseDto, "Video saved successfully"));
        } else {
            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not found"));
        }
    }

    // 동영상 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteVideo(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not authenticated"));
        }

        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            ResponseDto<Void> response = videoService.deleteByIdAndUser(id, user);
            return response.getResultCode().equals("SUCCESS")
                    ? ResponseEntity.ok(response)
                    : ResponseEntity.status(404).body(response);
        } else {
            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not found"));
        }
    }

    // 동영상 재생 & 중단 (시청 기록 업데이트)
    @PostMapping("/watch/{id}")
    public ResponseEntity<ResponseDto<WatchHistoryResponseDto>> updateWatchHistory(@PathVariable Long id, @RequestBody WatchHistory watchHistory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not authenticated"));
        }

        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            ResponseDto<WatchHistoryResponseDto> response = watchHistoryService.updateWatchHistory(user, id, watchHistory.getWatchDuration(), watchHistory.getLastWatchedPosition());
            if ("SUCCESS".equals(response.getResultCode())) {
                videoService.incrementViews(id, user);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body(response);
            }
        } else {
            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not found"));
        }
    }

    // 동영상 세부 정보 조회 (현재 동영상에 연결된 광고 목록까지 조회 가능)
//    @GetMapping("/play/{id}")
//    public ResponseEntity<ResponseDto<VideoResponseDto>> playVideo(@PathVariable Long id) {
//        // 비디오 정보 조회
//        ResponseDto<Optional<Video>> response = videoService.findById(id);
//        if (response.getResultCode().equals("SUCCESS") && response.getData().isPresent()) {
//            Video video = response.getData().get();
//
//            // 광고 목록을 가져옵니다.
//            ResponseDto<List<VideoAd>> adResponse = videoAdService.findByVideo(video);
//            if (!adResponse.getResultCode().equals("SUCCESS")) {
//                return ResponseEntity.status(404).body(new ResponseDto<>("ERROR", null, "Ads not found"));
//            }
//            List<Ad> ads = adResponse.getData().stream()
//                    .map(VideoAd::getAd)
//                    .collect(Collectors.toList());
//
//            VideoResponseDto videoResponseDto = new VideoResponseDto(video, ads);
//            return ResponseEntity.ok(new ResponseDto<>("SUCCESS", videoResponseDto, "Video with ads retrieved successfully"));
//        } else {
//            return ResponseEntity.status(404).body(new ResponseDto<>("ERROR", null, "Video not found"));
//        }
//    }
}