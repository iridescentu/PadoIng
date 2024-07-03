package com.sparta.padoing.controller;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.dto.response.VideoResponseDto;
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

import java.time.LocalDateTime;
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
        ResponseDto<List<Video>> response = videoService.findAll();
        List<VideoResponseDto> videoResponseDtoList = response.getData().stream()
                .map(VideoResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ResponseDto<>("SUCCESS", videoResponseDtoList, "All videos retrieved successfully"));
    }

    // 동영상 ID로 동영상 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<VideoResponseDto>> getVideoById(@PathVariable Long id) {
        ResponseDto<Optional<Video>> response = videoService.findById(id);
        if (response.getResultCode().equals("SUCCESS") && response.getData().isPresent()) {
            VideoResponseDto videoResponseDto = new VideoResponseDto(response.getData().get());
            return ResponseEntity.ok(new ResponseDto<>("SUCCESS", videoResponseDto, "Video retrieved successfully"));
        } else {
            return ResponseEntity.status(404).body(new ResponseDto<>("ERROR", null, "Video not found"));
        }
    }

    // 동영상 업로드
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
            video.setUploadDate(LocalDateTime.now());

            // 동영상을 업로드하면 UPLOADER 역할 부여
            user.addRole(Role.UPLOADER);
            userService.saveOrUpdateUser(user);

            Video savedVideo = videoService.save(video).getData();
            VideoResponseDto videoResponseDto = new VideoResponseDto(savedVideo);
            return ResponseEntity.status(201).body(new ResponseDto<>("SUCCESS", videoResponseDto, "Video saved successfully"));
        } else {
            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not found"));
        }
    }

    // 동영상 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteVideo(@PathVariable Long id) {
        ResponseDto<Void> response = videoService.deleteById(id);
        if ("SUCCESS".equals(response.getResultCode())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
        }
    }

    // 시청 기록 업데이트
    @PostMapping("/watch/{id}")
    public ResponseEntity<ResponseDto<WatchHistory>> updateWatchHistory(@PathVariable Long id, @RequestBody WatchHistory watchHistory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not authenticated"));
        }

        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            ResponseDto<WatchHistory> response = watchHistoryService.updateWatchHistory(user, id, watchHistory.getWatchDuration(), watchHistory.getLastWatchedPosition());
            if ("SUCCESS".equals(response.getResultCode())) {
                videoService.incrementViews(id, user); // 조회수 증가
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body(response);
            }
        } else {
            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not found"));
        }
    }

    // 동영상 재생
    @GetMapping("/play/{id}")
    public ResponseEntity<ResponseDto<VideoResponseDto>> playVideo(@PathVariable Long id) {
        ResponseDto<Optional<Video>> response = videoService.findById(id);
        if (response.getResultCode().equals("SUCCESS") && response.getData().isPresent()) {
            Video video = response.getData().get();

            // 광고 목록을 가져옵니다.
            ResponseDto<List<VideoAd>> adResponse = videoAdService.findByVideo(video);
            if (!adResponse.getResultCode().equals("SUCCESS")) {
                return ResponseEntity.status(404).body(new ResponseDto<>("ERROR", null, "Ads not found"));
            }
            List<Ad> ads = adResponse.getData().stream()
                    .map(VideoAd::getAd)
                    .collect(Collectors.toList());

            VideoResponseDto videoResponseDto = new VideoResponseDto(video, ads);
            return ResponseEntity.ok(new ResponseDto<>("SUCCESS", videoResponseDto, "Video with ads retrieved successfully"));
        } else {
            return ResponseEntity.status(404).body(new ResponseDto<>("ERROR", null, "Video not found"));
        }
    }
}