package com.sparta.padoing.dto.response;

import com.sparta.padoing.model.Ad;
import com.sparta.padoing.model.Video;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class VideoResponseDto {
    private String title;
    private String description;
    private int views;
    private String uploadDate; // 포맷된 날짜로 변경
    private String duration; // 포맷된 시간으로 변경
    private Long id;
    private String userName;
    private boolean isActive; // 활성 상태 추가
    private List<Ad> ads; // 광고 목록 추가

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");

    public VideoResponseDto(Video video) {
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.views = video.getViews();
        this.uploadDate = formatDate(video.getUploadDate());
        this.duration = formatDuration(video.getDuration());
        this.id = video.getId();
        this.userName = video.getUser().getName();
        this.isActive = video.isActive(); // 활성 상태 초기화
    }

    public VideoResponseDto(Video video, List<Ad> ads) {
        this(video); // 기존 생성자를 호출하여 공통 필드를 초기화
        this.ads = ads;
    }

    private String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    private String formatDuration(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        StringBuilder formattedDuration = new StringBuilder();
        if (hours > 0) {
            formattedDuration.append(hours).append("시간 ");
        }
        if (minutes > 0) {
            formattedDuration.append(minutes).append("분 ");
        }
        formattedDuration.append(secs).append("초");
        return formattedDuration.toString();
    }
}