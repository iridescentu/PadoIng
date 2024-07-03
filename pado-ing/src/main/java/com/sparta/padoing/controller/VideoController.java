package com.sparta.padoing.controller;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.dto.response.WatchHistoryResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.WatchHistory;
import com.sparta.padoing.service.UserService;
import com.sparta.padoing.service.VideoService;
import com.sparta.padoing.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @Autowired
    private WatchHistoryService watchHistoryService;

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
}