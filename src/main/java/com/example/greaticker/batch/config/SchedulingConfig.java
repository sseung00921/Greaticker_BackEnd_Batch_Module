package com.example.greaticker.batch.config;

import jdk.jfr.Enabled;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class SchedulingConfig {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Autowired
    public SchedulingConfig(JobLauncher jobLauncher, @Qualifier("userWhoDoseNotGetStickerYesterdayRefreshProjectJob")Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Scheduled(cron = "0 * * * * *") // 매 시간마다 실행
    public void runUserWhoDoseNotGetStickerYesterdayRefreshProjectJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException |
                 JobInstanceAlreadyCompleteException | JobRestartException e) {
            System.out.println("Initialization failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
