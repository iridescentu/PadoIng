package com.sparta.padoing.model.id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class AdStmtId implements Serializable {
    private Long ad;
    private Long user;
    private LocalDate createdAt;
}