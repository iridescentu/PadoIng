package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.WatchHistory;

public interface WatchHistoryService {
    ResponseDto<WatchHistory> updateWatchHistory(User user, Long videoId, int watchDuration, int lastWatchedPosition);
}