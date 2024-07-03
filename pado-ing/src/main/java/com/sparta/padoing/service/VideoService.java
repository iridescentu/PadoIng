package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.dto.response.VideoResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.Video;
import com.sparta.padoing.model.VideoAd;

import java.util.List;
import java.util.Optional;

public interface VideoService {
    ResponseDto<List<VideoResponseDto>> findAll();
    ResponseDto<Optional<VideoResponseDto>> findById(Long id);
    ResponseDto<Video> save(Video video);
    ResponseDto<Void> deleteByIdAndUser(Long id, User user); // 동영상을 올린 사용자만 동영상을 삭제할 수 있도록
    void incrementViews(Long videoId, User currentUser);
    void insertAds(Video video);
    List<VideoAd> findVideoAdsByVideo(Video video);
    ResponseDto<List<VideoResponseDto>> findActiveVideos(); // 활성 상태의 동영상을 조회
}