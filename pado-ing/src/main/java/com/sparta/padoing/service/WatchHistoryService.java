package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.dto.response.WatchHistoryResponseDto;
import com.sparta.padoing.model.User;

public interface WatchHistoryService {
    ResponseDto<WatchHistoryResponseDto> updateWatchHistory(User user, Long videoId, int watchDuration, int lastWatchedPosition);
}