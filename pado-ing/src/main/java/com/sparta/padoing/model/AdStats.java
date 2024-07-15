package com.sparta.padoing.model;

import com.sparta.padoing.model.id.AdStatsId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "ad_stats")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@IdClass(AdStatsId.class)
public class AdStats {

    @Id
    @ManyToOne
    @JoinColumn(name = "video_ad_id")
    private VideoAd videoAd;

    @Id
    @CreatedDate
    @Column(name = "date", updatable = false)
    private LocalDate date;

    @Column(name = "ad_view")
    private int adView;

    public static AdStats of(VideoAd videoAd, int adView, LocalDate date) {
        return AdStats.builder()
                .videoAd(videoAd)
                .date(date)
                .adView(adView)
                .build();
    }

    public void setAdView(int adView) {
        this.adView = adView;
    }
}