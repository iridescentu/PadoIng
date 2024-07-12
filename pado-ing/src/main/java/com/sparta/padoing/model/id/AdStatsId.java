package com.sparta.padoing.model.id;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class AdStatsId implements Serializable {
    private Long videoAd;
    private LocalDate date;

    // 기본 생성자
    public AdStatsId() {}

    public AdStatsId(Long videoAd, LocalDate date) {
        this.videoAd = videoAd;
        this.date = date;
    }

    // equals 및 hashCode 메서드 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdStatsId adStatsId = (AdStatsId) o;
        return Objects.equals(videoAd, adStatsId.videoAd) && Objects.equals(date, adStatsId.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoAd, date);
    }
}