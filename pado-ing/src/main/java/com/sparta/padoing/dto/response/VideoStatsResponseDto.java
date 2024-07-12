package com.sparta.padoing.dto.response;

import com.sparta.padoing.model.VideoStats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoStatsResponseDto {
    private Long videoId;
    private String videoTitle;
    private LocalDate date;
    private int videoView;
    private long playTime;

    public static VideoStatsResponseDto fromEntity(VideoStats videoStats) {
        return VideoStatsResponseDto.builder()
                .videoId(videoStats.getVideo().getId())
                .videoTitle(videoStats.getVideo().getTitle())
                .date(videoStats.getDate())
                .videoView(videoStats.getVideoView())
                .playTime(videoStats.getPlayTime())
                .build();
    }
}