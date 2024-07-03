package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.dto.response.VideoResponseDto;
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
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoAdRepository videoAdRepository;

    @Autowired
    private AdRepository adRepository;

    // 모든 비디오 조회
    @Override
    public ResponseDto<List<VideoResponseDto>> findAll() {
        List<Video> videos = videoRepository.findAllByIsDeletedFalse();
        List<VideoResponseDto> videoResponseDtos = videos.stream()
                .map(video -> {
                    List<VideoAd> videoAds = videoAdRepository.findByVideo(video);
                    List<Ad> ads = videoAds.stream().map(VideoAd::getAd).collect(Collectors.toList());
                    return new VideoResponseDto(video, ads);
                })
                .collect(Collectors.toList());
        return new ResponseDto<>("SUCCESS", videoResponseDtos, "All videos retrieved successfully");
    }

    // 비디오 ID로 비디오 조회
    @Override
    public ResponseDto<Optional<VideoResponseDto>> findById(Long id) {
        Optional<Video> videoOpt = videoRepository.findByIdAndIsDeletedFalse(id);
        if (videoOpt.isPresent()) {
            Video video = videoOpt.get();
            List<VideoAd> videoAds = videoAdRepository.findByVideo(video);
            List<Ad> ads = videoAds.stream().map(VideoAd::getAd).collect(Collectors.toList());
            VideoResponseDto videoResponseDto = new VideoResponseDto(video, ads);
            return new ResponseDto<>("SUCCESS", Optional.of(videoResponseDto), "Video retrieved successfully");
        } else {
            return new ResponseDto<>("NOT_FOUND", Optional.empty(), "Video not found");
        }
    }

    @Override
    public ResponseDto<Video> save(Video video) {
        // 비디오 저장
        Video savedVideo = videoRepository.save(video);

        // 광고 삽입 로직
        insertAds(savedVideo);

        return new ResponseDto<>("SUCCESS", savedVideo, "Video saved successfully");
    }

    // 비디오 ID와 User로 비디오 삭제
    @Override
    public ResponseDto<Void> deleteByIdAndUser(Long id, User user) {
        Optional<Video> videoOpt = videoRepository.findById(id);
        if (videoOpt.isPresent()) {
            Video video = videoOpt.get();
            if (video.getUser().equals(user)) {
                video.setDeleted(true); // 소프트 삭제
                video.setActive(false); // 비활성화
                videoRepository.save(video);
                return new ResponseDto<>("SUCCESS", null, "Video deleted successfully");
            } else {
                return new ResponseDto<>("FORBIDDEN", null, "You are not allowed to delete this video");
            }
        } else {
            return new ResponseDto<>("NOT_FOUND", null, "Video not found");
        }
    }

    // 조회수 증가
    @Override
    public void incrementViews(Long videoId, User currentUser) {
        Optional<Video> videoOpt = videoRepository.findById(videoId);
        if (videoOpt.isPresent()) {
            Video video = videoOpt.get();

            // 어뷰징 방지
            if (!video.getUser().getId().equals(currentUser.getId())) {
                video.setViews(video.getViews() + 1);
                videoRepository.save(video);
            }
        }
    }

    // 광고 삽입 로직
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

    // 비디오에 연결된 광고 목록 조회
    @Override
    public List<VideoAd> findVideoAdsByVideo(Video video) {
        return videoAdRepository.findByVideo(video);
    }

    // 활성 상태인 동영상 조회
    @Override
    public ResponseDto<List<VideoResponseDto>> findActiveVideos() {
        List<Video> activeVideos = videoRepository.findByIsActiveTrueAndIsDeletedFalse();
        List<VideoResponseDto> videoResponseDtos = activeVideos.stream()
                .map(video -> {
                    List<VideoAd> videoAds = videoAdRepository.findByVideo(video);
                    List<Ad> ads = videoAds.stream().map(VideoAd::getAd).collect(Collectors.toList());
                    return new VideoResponseDto(video, ads);
                })
                .collect(Collectors.toList());
        return new ResponseDto<>("SUCCESS", videoResponseDtos, "Active videos retrieved successfully");
    }
}