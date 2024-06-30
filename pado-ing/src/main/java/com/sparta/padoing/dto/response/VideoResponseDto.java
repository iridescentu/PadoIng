package com.sparta.padoing.dto;

import com.sparta.padoing.model.Video;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoResponseDto {
    private String title;
    private String description;
    private int views;
    private LocalDateTime uploadDate;
    private int duration;
    private Long id;

    // Constructor to create VideoResponseDto from Video entity
    public VideoResponseDto(Video video) {
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.views = video.getViews();
        this.uploadDate = video.getUploadDate();
        this.duration = video.getDuration();
        this.id = video.getId();
    }
}