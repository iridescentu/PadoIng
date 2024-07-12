package com.sparta.padoing.repository;

import com.sparta.padoing.model.VideoStats;
import com.sparta.padoing.model.id.VideoStatsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VideoStatsRepository extends JpaRepository<VideoStats, VideoStatsId> {
    List<VideoStats> findTop5ByVideo_User_IdAndCreatedAtBetweenOrderByDailyViewCountDesc(Long userId, LocalDate startDate, LocalDate endDate);
    List<VideoStats> findTop5ByVideo_User_IdAndCreatedAtBetweenOrderByDailyPlayTimeDesc(Long userId, LocalDate startDate, LocalDate endDate);
}