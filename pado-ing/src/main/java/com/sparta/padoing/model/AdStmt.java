package com.sparta.padoing.model;

import com.sparta.padoing.model.id.AdStmtId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "ad_stmt")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@IdClass(AdStmtId.class)
public class AdStmt {

    @Id
    @ManyToOne
    @JoinColumn(name = "video_ad_id")
    private VideoAd videoAd;

    @Id
    @Column(name = "date", updatable = false)
    private LocalDate date;

    @Column(name = "ad_stmt")
    private int adStmt;

    @Column(name = "ad_view")
    private long adView;

    public static AdStmt of(VideoAd videoAd, int adStmt, long adView) {
        return AdStmt.builder()
                .videoAd(videoAd)
                .date(LocalDate.now())
                .adStmt(adStmt)
                .adView(adView)
                .build();
    }

    public long getAdView() {
        return adView;
    }
}