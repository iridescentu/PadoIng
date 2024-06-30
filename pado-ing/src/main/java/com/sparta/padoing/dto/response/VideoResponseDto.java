// User가 여러 권한을 가질 수 있도록 수정
package com.sparta.padoing.dto.response;

import com.sparta.padoing.model.Video;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VideoResponseDto {
    private String title;
    private String description;
    private int views;
    private LocalDateTime uploadDate;
    private int duration;
    private Long id;
    private String userName;

    public VideoResponseDto(Video video) {
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.views = video.getViews();
        this.uploadDate = video.getUploadDate();
        this.duration = video.getDuration();
        this.id = video.getId();
        this.userName = video.getUser().getName();
    }
}