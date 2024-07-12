package com.sparta.padoing.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatsRequest {
    private String period; // "1일", "1주일", "1달" 중 하나
}