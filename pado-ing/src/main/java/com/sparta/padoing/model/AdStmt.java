package com.sparta.padoing.model;

import com.sparta.padoing.model.id.AdStmtId;
import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "videoad_id")
    private VideoAd videoAd;

    @Id
    @Column(name = "date", updatable = false)
    private LocalDate date;

    @Column(name = "ad_stmt")
    private int adStmt;

    @Column(name = "ad_view")
    private long adView;

    public static AdStmt of(VideoAd videoAd, int adStmt, LocalDate date) {
        return AdStmt.builder()
                .videoAd(videoAd)
                .date(date)
                .adStmt(adStmt)
                .build();
    }
}