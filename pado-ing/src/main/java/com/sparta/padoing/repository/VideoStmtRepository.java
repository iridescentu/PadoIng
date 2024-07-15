//package com.sparta.padoing.repository;
//
//import com.sparta.padoing.model.VideoStmt;
//import com.sparta.padoing.model.id.VideoStmtId;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Repository
//public interface VideoStmtRepository extends JpaRepository<VideoStmt, VideoStmtId> {
//    List<VideoStmt> findByVideo_User_IdAndCreatedAtBetween(Long userId, LocalDate startDate, LocalDate endDate);
//}

package com.sparta.padoing.repository;

import com.sparta.padoing.model.VideoStmt;
import com.sparta.padoing.model.id.VideoStmtId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VideoStmtRepository extends JpaRepository<VideoStmt, VideoStmtId> {
    List<VideoStmt> findByVideo_User_IdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}