package com.sparta.padoing.dto.response;

import com.sparta.padoing.model.AdStats;
import com.sparta.padoing.model.VideoAd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdStatsResponseDto {
    private Long videoAdId;
    private Long videoId;
    private String videoTitle;
    private String adTitle;
    private LocalDate date;
    private int adView;

    public static AdStatsResponseDto fromEntity(AdStats adStats) {
        VideoAd videoAd = adStats.getVideoAd();
        return AdStatsResponseDto.builder()
                .videoAdId(videoAd.getId())
                .videoId(videoAd.getVideo().getId())
                .videoTitle(videoAd.getVideo().getTitle())
                .adTitle(videoAd.getAd().getTitle())
                .date(adStats.getDate())
                .adView(adStats.getAdView())
                .build();
    }
}