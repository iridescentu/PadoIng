package com.sparta.padoing.model;

import com.sparta.padoing.model.id.VideoStatsId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "video_stats")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@IdClass(VideoStatsId.class)
public class VideoStats {

    @Id
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @Id
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    private int dailyViewCount;
    private long dailyPlayTime;

    public static VideoStats of(Video video, int dailyViewCount, long dailyPlayTime) {
        return VideoStats.builder()
                .video(video)
                .createdAt(LocalDate.now())
                .dailyViewCount(dailyViewCount)
                .dailyPlayTime(dailyPlayTime)
                .build();
    }
}