package com.sparta.padoing.controller;

import com.sparta.padoing.dto.VideoResponseDto;
import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.Video;
import com.sparta.padoing.service.UserService;
import com.sparta.padoing.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<Video>>> getAllVideos() {
        ResponseDto<List<Video>> response = videoService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Optional<Video>>> getVideoById(@PathVariable Long id) {
        ResponseDto<Optional<Video>> response = videoService.findById(id);
        if ("SUCCESS".equals(response.getResultCode())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
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
}