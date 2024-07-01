package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.Video;
import com.sparta.padoing.model.VideoAd;
import com.sparta.padoing.repository.VideoAdRepository;
import com.sparta.padoing.service.VideoAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoAdServiceImpl implements VideoAdService {

    @Autowired
    private VideoAdRepository videoAdRepository;

    @Override
    public ResponseDto<List<VideoAd>> findByVideo(Video video) {
        List<VideoAd> videoAds = videoAdRepository.findByVideo(video);
        if (!videoAds.isEmpty()) {
            return new ResponseDto<>("SUCCESS", videoAds, "Ads retrieved successfully");
        } else {
            return new ResponseDto<>("NOT_FOUND", null, "No ads found for the video");
        }
    }
}