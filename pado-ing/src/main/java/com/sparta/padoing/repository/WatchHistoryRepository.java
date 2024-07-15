package com.sparta.padoing.repository;

import com.sparta.padoing.model.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {
    List<WatchHistory> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);
}