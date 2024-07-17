//package com.sparta.padoing.service.impl;
//
//import com.sparta.padoing.dto.response.ResponseDto;
//import com.sparta.padoing.model.AdStmt;
//import com.sparta.padoing.model.AdStats;
//import com.sparta.padoing.model.VideoAd;
//import com.sparta.padoing.repository.AdStmtRepository;
//import com.sparta.padoing.repository.AdStatsRepository;
//import com.sparta.padoing.repository.VideoAdRepository;
//import com.sparta.padoing.service.AdStmtService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@Transactional
//public class AdStmtServiceImpl implements AdStmtService {
//
//    @Autowired
//    private AdStmtRepository adStmtRepository;
//
//    @Autowired
//    private AdStatsRepository adStatsRepository;
//
//    @Autowired
//    private VideoAdRepository videoAdRepository;
//
//    @Override
//    public ResponseDto<Map<String, Object>> getAdStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
//        generateAdStmt(userId, startDate, endDate);
//
//        List<AdStmt> adStmts = adStmtRepository.findByVideoAd_Video_User_IdAndDateBetween(userId, startDate, endDate);
//        if (adStmts.isEmpty()) {
//            return new ResponseDto<>("NO_DATA", null, "조회할 데이터가 없습니다.", startDate, endDate);
//        }
//
//        Map<String, Object> responseMap = new LinkedHashMap<>();
//        long totalStmt = 0;
//
//        for (AdStmt adStmt : adStmts) {
//            long adStmtValue = adStmt.getAdStmt();
//            totalStmt += adStmtValue;
//
//            Map<String, Object> adDetails = new LinkedHashMap<>();
//            adDetails.put("videoTitle", adStmt.getVideoAd().getVideo().getTitle());
//            adDetails.put("adTitle", adStmt.getVideoAd().getAd().getTitle());
//            adDetails.put("adStmt", adStmtValue);
//            responseMap.put("Ad " + adStmt.getVideoAd().getId(), adDetails);
//        }
//
//        responseMap.put("totalStmt", totalStmt);
//        return new ResponseDto<>("SUCCESS", responseMap, "Ad statements retrieved successfully", startDate, endDate);
//    }
//
//    @Override
//    public ResponseDto<Map<String, Object>> getAdRevenue(Long userId, LocalDate startDate, LocalDate endDate) {
//        generateAdStmt(userId, startDate, endDate);
//
//        List<AdStmt> adStmts = adStmtRepository.findByVideoAd_Video_User_IdAndDateBetween(userId, startDate, endDate);
//        if (adStmts.isEmpty()) {
//            return new ResponseDto<>("NO_DATA", null, "해당 조회 날짜에 조회할 데이터가 없습니다.", startDate, endDate);
//        }
//
//        Map<String, Object> responseMap = new LinkedHashMap<>();
//        long totalRevenue = 0;
//
//        for (AdStmt adStmt : adStmts) {
//            long adRevenue = calculateAdRevenue(adStmt);
//            totalRevenue += adRevenue;
//
//            Map<String, Object> adDetails = new LinkedHashMap<>();
//            adDetails.put("videoTitle", adStmt.getVideoAd().getVideo().getTitle());
//            adDetails.put("adTitle", adStmt.getVideoAd().getAd().getTitle());
//            adDetails.put("adRevenue", adRevenue);
//            responseMap.put("Ad " + adStmt.getVideoAd().getId(), adDetails);
//        }
//
//        responseMap.put("totalRevenue", totalRevenue);
//        return new ResponseDto<>("SUCCESS", responseMap, "Ad revenue calculated successfully", startDate, endDate);
//    }
//
//    private long calculateAdRevenue(AdStmt adStmt) {
//        long viewCount = adStmt.getAdView();
//        long rate;
//
//        if (viewCount < 100000) {
//            rate = 10;
//        } else if (viewCount < 500000) {
//            rate = 12;
//        } else if (viewCount < 1000000) {
//            rate = 15;
//        } else {
//            rate = 20;
//        }
//
//        return (viewCount * rate) / 100;
//    }
//
//    @Override
//    public void generateAdStmt(Long userId, LocalDate startDate, LocalDate endDate) {
//        List<VideoAd> videoAds = videoAdRepository.findByVideo_User_Id(userId);
//        for (VideoAd videoAd : videoAds) {
//            long totalViewCount = 0;
//            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
//                List<AdStats> adStatsList = adStatsRepository.findByVideoAd_IdAndDateBetween(videoAd.getId(), date, date);
//                for (AdStats adStats : adStatsList) {
//                    totalViewCount += adStats.getAdView();
//                }
//            }
//            int adStmtValue = (int) totalViewCount;
//            AdStmt stmt = new AdStmt(videoAd, endDate, adStmtValue, adStmtValue);
//            adStmtRepository.save(stmt);
//        }
//    }
//}

package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdStmt;
import com.sparta.padoing.model.AdStats;
import com.sparta.padoing.model.VideoAd;
import com.sparta.padoing.repository.AdStmtRepository;
import com.sparta.padoing.repository.AdStatsRepository;
import com.sparta.padoing.repository.VideoAdRepository;
import com.sparta.padoing.service.AdStmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdStmtServiceImpl implements AdStmtService {

    @Autowired
    private AdStmtRepository adStmtRepository;

    @Autowired
    private AdStatsRepository adStatsRepository;

    @Autowired
    private VideoAdRepository videoAdRepository;

    @Override
    public ResponseDto<Map<String, Object>> getAdStmtByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        generateAdStmt(userId, startDate, endDate);

        List<AdStmt> adStmts = adStmtRepository.findByVideoAd_Video_User_IdAndDateBetween(userId, startDate, endDate);
        if (adStmts.isEmpty()) {
            return new ResponseDto<>("NO_DATA", null, "조회할 데이터가 없습니다.", startDate, endDate);
        }

        Map<String, Object> responseMap = new LinkedHashMap<>();
        long totalStmt = 0;

        for (AdStmt adStmt : adStmts) {
            long adStmtValue = adStmt.getAdStmt();
            totalStmt += adStmtValue;

            Map<String, Object> adDetails = new LinkedHashMap<>();
            adDetails.put("videoTitle", adStmt.getVideoAd().getVideo().getTitle());
            adDetails.put("adTitle", adStmt.getVideoAd().getAd().getTitle());
            adDetails.put("adStmt", adStmtValue);
            responseMap.put("Ad " + adStmt.getVideoAd().getId(), adDetails);
        }

        responseMap.put("totalStmt", totalStmt);
        return new ResponseDto<>("SUCCESS", responseMap, "Ad statements retrieved successfully", startDate, endDate);
    }

    @Override
    public ResponseDto<Map<String, Object>> getAdRevenue(Long userId, LocalDate startDate, LocalDate endDate) {
        generateAdStmt(userId, startDate, endDate);

        List<AdStmt> adStmts = adStmtRepository.findByVideoAd_Video_User_IdAndDateBetween(userId, startDate, endDate);
        if (adStmts.isEmpty()) {
            return new ResponseDto<>("NO_DATA", null, "해당 조회 날짜에 조회할 데이터가 없습니다.", startDate, endDate);
        }

        Map<String, Object> responseMap = new LinkedHashMap<>();
        long totalRevenue = 0;
        for (AdStmt adStmt : adStmts) {
            long adRevenue = calculateAdRevenue(adStmt);
            totalRevenue += adRevenue;

            Map<String, Object> adDetails = new LinkedHashMap<>();
            adDetails.put("videoTitle", adStmt.getVideoAd().getVideo().getTitle());
            adDetails.put("adTitle", adStmt.getVideoAd().getAd().getTitle());
            adDetails.put("adRevenue", adRevenue);
            responseMap.put("Ad " + adStmt.getVideoAd().getId(), adDetails);
        }

        responseMap.put("totalRevenue", totalRevenue);
        return new ResponseDto<>("SUCCESS", responseMap, "Ad revenue calculated successfully", startDate, endDate);
    }

    private long calculateAdRevenue(AdStmt adStmt) {
        long viewCount = adStmt.getAdView();
        long rate;

        if (viewCount < 100000) {
            rate = 10;
        } else if (viewCount < 500000) {
            rate = 12;
        } else if (viewCount < 1000000) {
            rate = 15;
        } else {
            rate = 20;
        }

        return (viewCount * rate) / 100;
    }

    @Override
    public void generateAdStmt(Long userId, LocalDate startDate, LocalDate endDate) {
        List<VideoAd> videoAds = videoAdRepository.findByVideo_User_Id(userId);
        for (VideoAd videoAd : videoAds) {
            long totalViewCount = 0;
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                List<AdStats> adStatsList = adStatsRepository.findByVideoAd_IdAndDateBetween(videoAd.getId(), date, date);
                for (AdStats adStats : adStatsList) {
                    totalViewCount += adStats.getAdView();
                }
            }
            int adStmtValue = (int) totalViewCount;
            AdStmt stmt = new AdStmt(videoAd, endDate, adStmtValue, adStmtValue);
            adStmtRepository.save(stmt);
            System.out.println("AdStmt saved for videoAdId: " + videoAd.getId() + ", adStmtValue: " + adStmtValue);
        }
    }
}