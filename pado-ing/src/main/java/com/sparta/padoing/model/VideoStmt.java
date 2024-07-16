package com.sparta.padoing.model;

import com.sparta.padoing.model.id.VideoStmtId;
import com.sparta.padoing.repository.VideoStatsRepository;
import jakarta.persistence.*;
import lombok.*;
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
    @Column(name = "date", updatable = false)
    private LocalDate date;

    @Column(name = "video_stmt")
    private int videoStmt;

    @Column(name = "ad_stmt")
    private int adStmt;

    public static VideoStmt of(Video video, int videoStmt, int adStmt) {
        return VideoStmt.builder()
                .video(video)
                .date(LocalDate.now())
                .videoStmt(videoStmt)
                .adStmt(adStmt)
                .build();
    }

    public Long getVideoId() {
        return video.getId();
    }

    public long getVideoView(VideoStatsRepository videoStatsRepository) {
        VideoStats videoStats = videoStatsRepository.findByVideo_IdAndDate(video.getId(), date).orElse(null);
        return videoStats != null ? videoStats.getVideoView() : 0;
    }

    public int getAdStmt() {
        return adStmt;
    }

    public void setAdStmt(int adStmt) {
        this.adStmt = adStmt;
    }
}