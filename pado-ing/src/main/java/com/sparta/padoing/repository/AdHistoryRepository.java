package com.sparta.padoing.repository;

import com.sparta.padoing.model.AdHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdHistoryRepository extends JpaRepository<AdHistory, Long> {
}
