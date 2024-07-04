package com.sparta.padoing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int views;

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    @Column(nullable = false)
    private int duration;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // 동영상 업로드할 때 isActive의 기본값을 true로 설정

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false; // 소프트 삭제 필드 추가
}