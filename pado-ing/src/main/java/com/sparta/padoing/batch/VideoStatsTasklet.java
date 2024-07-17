//package com.sparta.padoing.batch;
//
//import com.sparta.padoing.service.VideoStatsService;
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
//public class VideoStatsTasklet implements Tasklet {
//
//    @Autowired
//    private VideoStatsService videoStatsService;
//
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        LocalDate startDate = LocalDate.now().minusDays(1); // 예시로 하루 전 날짜 사용
//        LocalDate endDate = LocalDate.now();
//
//        // VideoStats 계산 로직
//        videoStatsService.generateVideoStats(null, startDate, endDate);
//        System.out.println("VideoStats 계산 수행");
//        return RepeatStatus.FINISHED;
//    }
//}

package com.sparta.padoing.batch;

import com.sparta.padoing.service.VideoStatsService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VideoStatsTasklet implements Tasklet {

    @Autowired
    private VideoStatsService videoStatsService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now();

        videoStatsService.generateVideoStats(null, startDate, endDate);
        return RepeatStatus.FINISHED;
    }
}