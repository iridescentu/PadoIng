package com.sparta.padoing.controller;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.Ad;
import com.sparta.padoing.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ads")
public class AdController {

    @Autowired
    private AdService adService;

    // 광고 생성
    @PostMapping
    public ResponseEntity<ResponseDto<Ad>> createAd(@RequestBody Ad ad) {
        ResponseDto<Ad> response = adService.save(ad);
        if ("SUCCESS".equals(response.getResultCode())) {
            return ResponseEntity.status(201).body(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

    // 광고 ID로 광고 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Ad>> getAdById(@PathVariable Long id) {
        ResponseDto<Ad> response = adService.findById(id);
        if ("SUCCESS".equals(response.getResultCode())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
        }
    }
}