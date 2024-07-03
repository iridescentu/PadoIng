package com.sparta.padoing.dto.response;

import com.sparta.padoing.model.Video;
import com.sparta.padoing.model.WatchHistory;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class WatchHistoryResponseDto {
    private Long videoId;
    private String videoTitle;
    private String videoDescription;
    private int videoViews;
    private String videoUploadDate; // 포맷된 날짜로 변경
    private String videoDuration; // 포맷된 시간으로 변경
    private String uploaderName;
    private String watchDuration; // 포맷된 시간으로 변경
    private String lastWatchedPosition; // 포맷된 시간으로 변경
    private String lastWatchedAt; // 포맷된 날짜로 변경

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");

    public WatchHistoryResponseDto(WatchHistory watchHistory) {
        Video video = watchHistory.getVideo();
        this.videoId = video.getId();
        this.videoTitle = video.isDeleted() ? "삭제된 동영상입니다" : video.getTitle();
        this.videoDescription = video.isDeleted() ? "" : video.getDescription();
        this.videoViews = video.getViews();
        this.videoUploadDate = video.isDeleted() ? "" : video.getUploadDate().format(DATE_TIME_FORMATTER);
        this.videoDuration = formatDuration(video.getDuration());
        this.uploaderName = video.getUser().getName();
        this.watchDuration = formatDuration(watchHistory.getWatchDuration());
        this.lastWatchedPosition = formatDuration(watchHistory.getLastWatchedPosition());
        this.lastWatchedAt = watchHistory.getLastWatchedAt().format(DATE_TIME_FORMATTER);
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