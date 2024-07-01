package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.Video;
import com.sparta.padoing.model.VideoAd;

import java.util.List;

public interface VideoAdService {
    ResponseDto<List<VideoAd>> findByVideo(Video video);
}