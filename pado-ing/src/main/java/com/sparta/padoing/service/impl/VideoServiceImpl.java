package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.Video;
import com.sparta.padoing.repository.VideoRepository;
import com.sparta.padoing.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public ResponseDto<List<Video>> findAll() {
        List<Video> videos = videoRepository.findAll();
        return new ResponseDto<>("SUCCESS", videos, "All videos retrieved successfully");
    }

    @Override
    public ResponseDto<Optional<Video>> findById(Long id) {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()) {
            return new ResponseDto<>("SUCCESS", video, "Video retrieved successfully");
        } else {
            return new ResponseDto<>("NOT_FOUND", null, "Video not found");
        }
    }

    @Override
    public ResponseDto<Video> save(Video video) {
        Video savedVideo = videoRepository.save(video);
        return new ResponseDto<>("SUCCESS", savedVideo, "Video saved successfully");
    }

    @Override
    public ResponseDto<Void> deleteById(Long id) {
        if (videoRepository.existsById(id)) {
            videoRepository.deleteById(id);
            return new ResponseDto<>("SUCCESS", null, "Video deleted successfully");
        } else {
            return new ResponseDto<>("NOT_FOUND", null, "Video not found");
        }
    }

    @Override
    public void incrementViews(Long videoId, User currentUser) {
        Optional<Video> videoOpt = videoRepository.findById(videoId);
        if (videoOpt.isPresent()) {
            Video video = videoOpt.get();
            if (!video.getUser().getId().equals(currentUser.getId())) {
                video.setViews(video.getViews() + 1);
                videoRepository.save(video);
            }
        }
    }
}