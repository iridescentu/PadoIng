package com.sparta.padoing.service.impl;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.Ad;
import com.sparta.padoing.repository.AdRepository;
import com.sparta.padoing.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdRepository adRepository;

    @Override
    public ResponseDto<Ad> save(Ad ad) {
        Ad savedAd = adRepository.save(ad);
        return new ResponseDto<>("SUCCESS", savedAd, "Ad saved successfully");
    }

    @Override
    public ResponseDto<Ad> findById(Long id) {
        Optional<Ad> ad = adRepository.findById(id);
        if (ad.isPresent()) {
            return new ResponseDto<>("SUCCESS", ad.get(), "Ad retrieved successfully");
        } else {
            return new ResponseDto<>("NOT_FOUND", null, "Ad not found");
        }
    }
}