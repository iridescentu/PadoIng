package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.*;
import com.sparta.padoing.repository.AdRepository;
import com.sparta.padoing.repository.VideoAdRepository;
import com.sparta.padoing.repository.VideoRepository;
import com.sparta.padoing.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoAdRepository videoAdRepository;

    @Autowired
    private AdRepository adRepository;

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

        // 광고 삽입 로직
        insertAds(savedVideo);

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

    @Override
    public void insertAds(Video video) {
        int videoDuration = video.getDuration();
        Random random = new Random();
        List<Ad> ads = adRepository.findAll();

        for (int i = 5 * 60; i <= videoDuration; i += 5 * 60) {
            if (!ads.isEmpty()) {
                Ad randomAd = ads.get(random.nextInt(ads.size()));

                VideoAd videoAd = new VideoAd();
                videoAd.setVideo(video);
                videoAd.setAd(randomAd);
                videoAd.setViews(0);

                videoAdRepository.save(videoAd);
            }
        }
    }

    @Override
    public List<VideoAd> findVideoAdsByVideo(Video video) {
        return videoAdRepository.findByVideo(video);
    }
}