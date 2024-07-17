//package com.sparta.padoing.batch;
//
//import com.sparta.padoing.service.AdStmtService;
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
//public class AdStmtTasklet implements Tasklet {
//
//    @Autowired
//    private AdStmtService adStmtService;
//
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        LocalDate startDate = LocalDate.now().minusDays(1);
//        LocalDate endDate = LocalDate.now();
//
//        adStmtService.generateAdStmt(null, startDate, endDate);
//        return RepeatStatus.FINISHED;
//    }
//}

package com.sparta.padoing.batch;

import com.sparta.padoing.service.AdStmtService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AdStmtTasklet implements Tasklet {

    @Autowired
    private AdStmtService adStmtService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now();
        Long userId = 1L;  // 테스트용 userId

        adStmtService.generateAdStmt(userId, startDate, endDate);
        System.out.println("AdStmt 계산 수행");
        return RepeatStatus.FINISHED;
    }
}