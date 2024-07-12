package com.sparta.padoing.model;

import com.sparta.padoing.model.id.VideoStatsId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "video_stats")
@Getter
@Setter
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
    @Column(name = "date", updatable = false)
    private LocalDate date;

    @Column(name = "video_view")
    private int videoView;

    @Column(name = "play_time")
    private long playTime;

    public static VideoStats of(Video video, int videoView, long playTime) {
        return VideoStats.builder()
                .video(video)
                .date(LocalDate.now())
                .videoView(videoView)
                .playTime(playTime)
                .build();
    }
}