package com.sparta.padoing.dto.response;

import com.sparta.padoing.model.Ad;
import com.sparta.padoing.model.Video;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class VideoResponseDto {
    private String title;
    private String description;
    private int views;
    private LocalDateTime uploadDate;
    private int duration;
    private Long id;
    private String userName;
    private List<Ad> ads; // 광고 목록 추가

    public VideoResponseDto(Video video) {
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.views = video.getViews();
        this.uploadDate = video.getUploadDate();
        this.duration = video.getDuration();
        this.id = video.getId();
        this.userName = video.getUser().getName();
    }

    public VideoResponseDto(Video video, List<Ad> ads) {
        this(video); // 기존 생성자를 호출하여 공통 필드를 초기화
        this.ads = ads;
    }
}