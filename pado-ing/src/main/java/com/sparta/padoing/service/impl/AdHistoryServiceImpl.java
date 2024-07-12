package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdHistory;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.VideoAd;
import com.sparta.padoing.repository.AdHistoryRepository;
import com.sparta.padoing.service.AdHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdHistoryServiceImpl implements AdHistoryService {

    @Autowired
    private AdHistoryRepository adHistoryRepository;

    @Override
    public ResponseDto<AdHistory> createAdHistory(User user, VideoAd videoAd) {
        AdHistory adHistory = AdHistory.of(user, videoAd);
        AdHistory savedAdHistory = adHistoryRepository.save(adHistory);
        return new ResponseDto<>("SUCCESS", savedAdHistory, "Ad history created successfully");
    }
}