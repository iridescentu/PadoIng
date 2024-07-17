//package com.sparta.padoing.batch;
//
//import com.sparta.padoing.service.VideoStmtService;
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
//public class VideoStmtTasklet implements Tasklet {
//
//    @Autowired
//    private VideoStmtService videoStmtService;
//
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        LocalDate startDate = LocalDate.now().minusDays(1);
//        LocalDate endDate = LocalDate.now();
//
//        videoStmtService.generateVideoStmt(null, startDate, endDate);
//        return RepeatStatus.FINISHED;
//    }
//}

package com.sparta.padoing.batch;

import com.sparta.padoing.service.VideoStmtService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VideoStmtTasklet implements Tasklet {

    @Autowired
    private VideoStmtService videoStmtService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now();
        Long userId = 1L;  // 테스트용 userId

        videoStmtService.generateVideoStmt(userId, startDate, endDate);
        System.out.println("VideoStmt 계산 수행");
        return RepeatStatus.FINISHED;
    }
}