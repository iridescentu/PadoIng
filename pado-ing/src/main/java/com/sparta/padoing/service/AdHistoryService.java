package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdHistory;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.VideoAd;

public interface AdHistoryService {
    ResponseDto<AdHistory> createAdHistory(User user, VideoAd videoAd);
}