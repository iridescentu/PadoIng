package com.sparta.padoing.repository;

import com.sparta.padoing.model.AdStmt;
import com.sparta.padoing.model.id.AdStmtId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdStmtRepository extends JpaRepository<AdStmt, AdStmtId> {
    List<AdStmt> findByVideoAd_Video_User_IdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}