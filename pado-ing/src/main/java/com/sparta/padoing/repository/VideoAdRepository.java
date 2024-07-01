package com.sparta.padoing.repository;

import com.sparta.padoing.model.Video;
import com.sparta.padoing.model.VideoAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoAdRepository extends JpaRepository<VideoAd, Long> {
    List<VideoAd> findByVideo(Video video);
}