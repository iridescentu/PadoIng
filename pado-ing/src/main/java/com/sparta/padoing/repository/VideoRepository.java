//package com.sparta.padoing.repository;
//
//import com.sparta.padoing.model.Video;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface VideoRepository extends JpaRepository<Video, Long> {
//    List<Video> findByIsActiveTrueAndIsDeletedFalse();
//    List<Video> findAllByIsDeletedFalse();
//    Optional<Video> findByIdAndIsDeletedFalse(Long id);
//}

package com.sparta.padoing.repository;

import com.sparta.padoing.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByIsActiveTrueAndIsDeletedFalse();
    List<Video> findAllByIsDeletedFalse();
    Optional<Video> findByIdAndIsDeletedFalse(Long id);
    List<Video> findByUser_Id(Long userId);  // 추가된 메서드
}