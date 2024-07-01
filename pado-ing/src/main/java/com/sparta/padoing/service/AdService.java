package com.sparta.padoing.service;

import com.sparta.padoing.dto.response.ResponseDto;
import com.sparta.padoing.model.Ad;

public interface AdService {
    ResponseDto<Ad> save(Ad ad);
    ResponseDto<Ad> findById(Long id);
}