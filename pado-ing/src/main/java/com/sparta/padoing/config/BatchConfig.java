package com.sparta.padoing.config;

import com.sparta.padoing.batch.AdStatsTasklet;
import com.sparta.padoing.batch.AdStmtTasklet;
import com.sparta.padoing.batch.VideoStatsTasklet;
import com.sparta.padoing.batch.VideoStmtTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private VideoStatsTasklet videoStatsTasklet;

    @Autowired
    private AdStatsTasklet adStatsTasklet;

    @Autowired
    private VideoStmtTasklet videoStmtTasklet;

    @Autowired
    private AdStmtTasklet adStmtTasklet;

    @Bean
    public Job statsJob() {
        return new JobBuilder("statsJob", jobRepository)
                .start(videoStatsStep())
                .next(adStatsStep())
                .next(videoStmtStep())
                .next(adStmtStep())
                .build();
    }

    @Bean
    public Step videoStatsStep() {
        return new StepBuilder("videoStatsStep", jobRepository)
                .tasklet(videoStatsTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step adStatsStep() {
        return new StepBuilder("adStatsStep", jobRepository)
                .tasklet(adStatsTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step videoStmtStep() {
        return new StepBuilder("videoStmtStep", jobRepository)
                .tasklet(videoStmtTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step adStmtStep() {
        return new StepBuilder("adStmtStep", jobRepository)
                .tasklet(adStmtTasklet, transactionManager)
                .build();
    }
}