package com.sparta.padoing.repository;

import com.sparta.padoing.model.VideoStats;
import com.sparta.padoing.model.id.VideoStatsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideoStatsRepository extends JpaRepository<VideoStats, VideoStatsId> {
    List<VideoStats> findTop5ByVideo_User_IdAndDateBetweenOrderByVideoViewDesc(Long userId, LocalDate startDate, LocalDate endDate);
    List<VideoStats> findTop5ByVideo_User_IdAndDateBetweenOrderByPlayTimeDesc(Long userId, LocalDate startDate, LocalDate endDate);
    Optional<VideoStats> findByVideo_IdAndDate(Long videoId, LocalDate date);
    boolean existsByVideo_User_Id(Long userId);

    // 커스텀 쿼리로 삭제 메서드 추가
    void deleteByVideo_User_IdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    // 새로운 메서드 추가
    List<VideoStats> findByVideo_IdAndDateBetween(Long videoId, LocalDate startDate, LocalDate endDate);
}