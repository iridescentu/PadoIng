package com.sparta.padoing.repository;

import com.sparta.padoing.model.AdHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdHistoryRepository extends JpaRepository<AdHistory, Long> {
    List<AdHistory> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);
}