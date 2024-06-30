package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.Video;

import java.util.List;
import java.util.Optional;

public interface VideoService {
    ResponseDto<List<Video>> findAll();
    ResponseDto<Optional<Video>> findById(Long id);
    ResponseDto<Video> save(Video video);
    ResponseDto<Void> deleteById(Long id);
    void incrementViews(Long videoId, User currentUser); // 조회수 증가 메서드
}