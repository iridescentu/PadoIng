package com.sparta.padoing.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StatsRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}