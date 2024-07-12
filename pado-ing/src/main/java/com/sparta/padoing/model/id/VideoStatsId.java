package com.sparta.padoing.model.id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class VideoStatsId implements Serializable {
    private Long video;
    private LocalDate createdAt;
}