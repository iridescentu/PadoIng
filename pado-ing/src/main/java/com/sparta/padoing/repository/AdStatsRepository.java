package com.sparta.padoing.repository;

import com.sparta.padoing.model.AdStats;
import com.sparta.padoing.model.id.AdStatsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdStatsRepository extends JpaRepository<AdStats, AdStatsId> {
    List<AdStats> findTop5ByVideoAd_Video_User_IdAndDateBetweenOrderByAdViewDesc(Long userId, LocalDate startDate, LocalDate endDate);
    Optional<AdStats> findByVideoAd_IdAndDate(Long videoAdId, LocalDate date);
    boolean existsByVideoAd_Video_User_Id(Long userId);
    List<AdStats> findByVideoAd_IdAndDateBetween(Long videoAdId, LocalDate startDate, LocalDate endDate);
    void deleteByVideoAd_Video_User_IdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}