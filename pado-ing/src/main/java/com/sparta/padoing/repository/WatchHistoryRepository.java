package com.sparta.padoing.repository;

import com.sparta.padoing.model.User;
import com.sparta.padoing.model.Video;
import com.sparta.padoing.model.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {
    Optional<WatchHistory> findByUserAndVideo(User user, Video video);
}