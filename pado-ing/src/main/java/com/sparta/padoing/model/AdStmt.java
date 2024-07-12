package com.sparta.padoing.model;

import com.sparta.padoing.model.id.AdStmtId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "ad_stmt")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@IdClass(AdStmtId.class)
public class AdStmt {

    @Id
    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    private int dailyViewCount;
    private long dailyPlayTime;

    private long totalEarnings;

    public static AdStmt of(Ad ad, User user, int dailyViewCount, long dailyPlayTime, long totalEarnings) {
        return AdStmt.builder()
                .ad(ad)
                .user(user)
                .createdAt(LocalDate.now())
                .dailyViewCount(dailyViewCount)
                .dailyPlayTime(dailyPlayTime)
                .totalEarnings(totalEarnings)
                .build();
    }
}