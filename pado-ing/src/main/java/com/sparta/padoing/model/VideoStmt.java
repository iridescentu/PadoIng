package com.sparta.padoing.model;

import com.sparta.padoing.model.id.VideoStmtId;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @Column(name = "date", updatable = false)
    private LocalDate date;

    @Column(name = "video_stmt")
    private int videoStmt;

    public static VideoStmt of(Video video, int videoStmt) {
        return VideoStmt.builder()
                .video(video)
                .date(LocalDate.now())
                .videoStmt(videoStmt)
                .build();
    }
}