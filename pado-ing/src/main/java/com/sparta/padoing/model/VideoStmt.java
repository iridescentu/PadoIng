package com.sparta.padoing.model;

import com.sparta.padoing.model.id.VideoStmtId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "video_stmt")
@Getter
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

    private double totalAmount;

    public static VideoStmt of(Video video, double totalAmount) {
        return VideoStmt.builder()
                .video(video)
                .createdAt(LocalDate.now())
                .totalAmount(totalAmount)
                .build();
    }
}