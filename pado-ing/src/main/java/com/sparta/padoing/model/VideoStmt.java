package com.sparta.padoing.model;

import com.sparta.padoing.model.id.VideoStmtId;
import com.sparta.padoing.repository.VideoStatsRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "video_stmt")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@IdClass(VideoStmtId.class)
public class VideoStmt {

    @Id
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @Id
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "video_stmt")
    private int videoStmt;

    @Transient
    private VideoStatsRepository videoStatsRepository;

    public static VideoStmt of(Video video, int videoStmt) {
        return VideoStmt.builder()
                .video(video)
                .createdAt(LocalDate.now())
                .videoStmt(videoStmt)
                .build();
    }

    public long calculateVideoRevenue() {
        long viewCount = getVideoView();
        double rate = 0;

        if (viewCount < 100000) {
            rate = 1.0;
        } else if (viewCount < 500000) {
            rate = 1.1;
        } else if (viewCount < 1000000) {
            rate = 1.3;
        } else {
            rate = 1.5;
        }

        return (long) (viewCount * rate);
    }

    public Long getVideoId() {
        return video.getId();
    }

    public long getVideoView() {
        VideoStats videoStats = videoStatsRepository.findByVideo_IdAndDate(video.getId(), createdAt).orElse(null);
        return videoStats != null ? videoStats.getVideoView() : 0;
    }

    @Autowired
    public void setVideoStatsRepository(VideoStatsRepository videoStatsRepository) {
        this.videoStatsRepository = videoStatsRepository;
    }
}