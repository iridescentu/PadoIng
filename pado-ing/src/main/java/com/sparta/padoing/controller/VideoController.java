//package com.sparta.padoing.controller;
//
//import com.sparta.padoing.dto.VideoResponseDto;
//import com.sparta.padoing.dto.response.ResponseDto;
//import com.sparta.padoing.model.User;
//import com.sparta.padoing.model.Video;
//import com.sparta.padoing.service.UserService;
//import com.sparta.padoing.service.VideoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/videos")
//public class VideoController {
//
//    @Autowired
//    private VideoService videoService;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping
//    public ResponseEntity<ResponseDto<List<Video>>> getAllVideos() {
//        ResponseDto<List<Video>> response = videoService.findAll();
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ResponseDto<Optional<Video>>> getVideoById(@PathVariable Long id) {
//        ResponseDto<Optional<Video>> response = videoService.findById(id);
//        if ("SUCCESS".equals(response.getResultCode())) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(404).body(response);
//        }
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<ResponseDto<VideoResponseDto>> createVideo(@RequestBody Video video) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not authenticated"));
//        }
//
//        String username = authentication.getName();
//        System.out.println("Authenticated username: " + username);
//
//        Optional<User> userOptional = userService.findByUsername(username);
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            video.setUser(user);
//            video.setUploadDate(LocalDateTime.now());
//            Video savedVideo = videoService.save(video).getData();
//            VideoResponseDto videoResponseDto = new VideoResponseDto(savedVideo);
//            return ResponseEntity.status(201).body(new ResponseDto<>("SUCCESS", videoResponseDto, "Video saved successfully"));
//        } else {
//            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not found"));
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ResponseDto<Void>> deleteVideo(@PathVariable Long id) {
//        ResponseDto<Void> response = videoService.deleteById(id);
//        if ("SUCCESS".equals(response.getResultCode())) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(404).body(response);
//        }
//    }
//}


// User의 민감한 정보(role, email, username 등)를 숨기고 name만 공개되도록
//package com.sparta.padoing.controller;
//
//import com.sparta.padoing.dto.response.ResponseDto;
//import com.sparta.padoing.dto.response.VideoResponseDto;
//import com.sparta.padoing.model.User;
//import com.sparta.padoing.model.Video;
//import com.sparta.padoing.service.UserService;
//import com.sparta.padoing.service.VideoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/videos")
//public class VideoController {
//
//    @Autowired
//    private VideoService videoService;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping
//    public ResponseEntity<ResponseDto<List<VideoResponseDto>>> getAllVideos() {
//        ResponseDto<List<Video>> response = videoService.findAll();
//        List<VideoResponseDto> videoResponseDtoList = response.getData().stream()
//                .map(VideoResponseDto::new)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(new ResponseDto<>("SUCCESS", videoResponseDtoList, "All videos retrieved successfully"));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ResponseDto<VideoResponseDto>> getVideoById(@PathVariable Long id) {
//        ResponseDto<Optional<Video>> response = videoService.findById(id);
//        if (response.getResultCode().equals("SUCCESS") && response.getData().isPresent()) {
//            VideoResponseDto videoResponseDto = new VideoResponseDto(response.getData().get());
//            return ResponseEntity.ok(new ResponseDto<>("SUCCESS", videoResponseDto, "Video retrieved successfully"));
//        } else {
//            return ResponseEntity.status(404).body(new ResponseDto<>("ERROR", null, "Video not found"));
//        }
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<ResponseDto<VideoResponseDto>> createVideo(@RequestBody Video video) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not authenticated"));
//        }
//
//        String username = authentication.getName();
//        System.out.println("Authenticated username: " + username);
//
//        Optional<User> userOptional = userService.findByUsername(username);
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            video.setUser(user);
//            video.setUploadDate(LocalDateTime.now());
//            Video savedVideo = videoService.save(video).getData();
//            VideoResponseDto videoResponseDto = new VideoResponseDto(savedVideo);
//            return ResponseEntity.status(201).body(new ResponseDto<>("SUCCESS", videoResponseDto, "Video saved successfully"));
//        } else {
//            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not found"));
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ResponseDto<Void>> deleteVideo(@PathVariable Long id) {
//        ResponseDto<Void> response = videoService.deleteById(id);
//        if ("SUCCESS".equals(response.getResultCode())) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(404).body(response);
//        }
//    }
//}

// User가 여러 권한을 가질 수 있도록 수정
//package com.sparta.padoing.controller;
//
//import com.sparta.padoing.dto.response.ResponseDto;
//import com.sparta.padoing.dto.response.VideoResponseDto;
//import com.sparta.padoing.model.Role;
//import com.sparta.padoing.model.User;
//import com.sparta.padoing.model.Video;
//import com.sparta.padoing.service.UserService;
//import com.sparta.padoing.service.VideoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/videos")
//public class VideoController {
//
//    @Autowired
//    private VideoService videoService;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping
//    public ResponseEntity<ResponseDto<List<VideoResponseDto>>> getAllVideos() {
//        ResponseDto<List<Video>> response = videoService.findAll();
//        List<VideoResponseDto> videoResponseDtoList = response.getData().stream()
//                .map(VideoResponseDto::new)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(new ResponseDto<>("SUCCESS", videoResponseDtoList, "All videos retrieved successfully"));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ResponseDto<VideoResponseDto>> getVideoById(@PathVariable Long id) {
//        ResponseDto<Optional<Video>> response = videoService.findById(id);
//        if (response.getResultCode().equals("SUCCESS") && response.getData().isPresent()) {
//            VideoResponseDto videoResponseDto = new VideoResponseDto(response.getData().get());
//            return ResponseEntity.ok(new ResponseDto<>("SUCCESS", videoResponseDto, "Video retrieved successfully"));
//        } else {
//            return ResponseEntity.status(404).body(new ResponseDto<>("ERROR", null, "Video not found"));
//        }
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<ResponseDto<VideoResponseDto>> createVideo(@RequestBody Video video) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not authenticated"));
//        }
//
//        String username = authentication.getName();
//        System.out.println("Authenticated username: " + username);
//
//        Optional<User> userOptional = userService.findByUsername(username);
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            video.setUser(user);
//            video.setUploadDate(LocalDateTime.now());
//
//            // Add UPLOADER role to the user
//            user.addRole(Role.UPLOADER);
//            userService.saveOrUpdateUser(user);
//
//            Video savedVideo = videoService.save(video).getData();
//            VideoResponseDto videoResponseDto = new VideoResponseDto(savedVideo);
//            return ResponseEntity.status(201).body(new ResponseDto<>("SUCCESS", videoResponseDto, "Video saved successfully"));
//        } else {
//            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not found"));
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ResponseDto<Void>> deleteVideo(@PathVariable Long id) {
//        ResponseDto<Void> response = videoService.deleteById(id);
//        if ("SUCCESS".equals(response.getResultCode())) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(404).body(response);
//        }
//    }
//}

package com.sparta.padoing.controller;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.dto.response.VideoResponseDto;
import com.sparta.padoing.model.Role;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.Video;
import com.sparta.padoing.model.WatchHistory;
import com.sparta.padoing.service.UserService;
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

    @GetMapping
    public ResponseEntity<ResponseDto<List<VideoResponseDto>>> getAllVideos() {
        ResponseDto<List<Video>> response = videoService.findAll();
        List<VideoResponseDto> videoResponseDtoList = response.getData().stream()
                .map(VideoResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ResponseDto<>("SUCCESS", videoResponseDtoList, "All videos retrieved successfully"));
    }

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

            // Add UPLOADER role to the user
            user.addRole(Role.UPLOADER);
            userService.saveOrUpdateUser(user);

            Video savedVideo = videoService.save(video).getData();
            VideoResponseDto videoResponseDto = new VideoResponseDto(savedVideo);
            return ResponseEntity.status(201).body(new ResponseDto<>("SUCCESS", videoResponseDto, "Video saved successfully"));
        } else {
            return ResponseEntity.status(403).body(new ResponseDto<>("ERROR", null, "User not found"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteVideo(@PathVariable Long id) {
        ResponseDto<Void> response = videoService.deleteById(id);
        if ("SUCCESS".equals(response.getResultCode())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
        }
    }

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
}