//package com.sparta.padoing.batch;
//
//import com.sparta.padoing.service.AdStatsService;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//
//@Component
//public class AdStatsTasklet implements Tasklet {
//
//    @Autowired
//    private AdStatsService adStatsService;
//
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        LocalDate startDate = LocalDate.now().minusDays(1); // 예시로 하루 전 날짜 사용
//        LocalDate endDate = LocalDate.now();
//
//        // AdStats 계산 로직
//        adStatsService.generateAdStats(null, startDate, endDate);
//        System.out.println("AdStats 계산 수행");
//        return RepeatStatus.FINISHED;
//    }
//}

package com.sparta.padoing.batch;

import com.sparta.padoing.service.AdStatsService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AdStatsTasklet implements Tasklet {

    @Autowired
    private AdStatsService adStatsService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now();

        adStatsService.generateAdStats(null, startDate, endDate);
        return RepeatStatus.FINISHED;
    }
}