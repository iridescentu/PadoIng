package com.sparta.padoing.controller;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.AdHistory;
import com.sparta.padoing.model.User;
import com.sparta.padoing.model.Ad;
import com.sparta.padoing.model.VideoAd;
import com.sparta.padoing.repository.VideoAdRepository;
import com.sparta.padoing.service.AdHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history/ads")
public class AdHistoryController {

    @Autowired
    private AdHistoryService adHistoryService;

    @Autowired
    private VideoAdRepository videoAdRepository;

    @PostMapping
    public ResponseDto<AdHistory> createAdHistory(@RequestBody User user, @RequestBody Ad ad, @RequestParam Long videoId) {
        VideoAd videoAd = videoAdRepository.findByVideo_IdAndAd_Id(videoId, ad.getId())
                .orElseThrow(() -> new RuntimeException("VideoAd not found"));
        return adHistoryService.createAdHistory(user, videoAd);
    }
}