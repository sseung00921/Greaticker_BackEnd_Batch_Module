package com.example.greaticker.batch.job;

import com.example.greaticker.batch.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class UserWhoDoseNotGetStickerYesterdayRefreshProjectJobconfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean(name = "userWhoDoseNotGetStickerYesterdayRefreshProjectJob")
    public Job userWhoDoseNotGetStickerYesterdayRefreshProjectJob(@Qualifier("userWhoDoseNotGetStickerYesterdayRefreshProjectStep") Step step) {
        return new JobBuilder("UserWhoDoseNotGetStickerYesterdayRefreshProjectJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean(name = "userWhoDoseNotGetStickerYesterdayRefreshProjectStep")
    public Step userWhoDoseNotGetStickerYesterdayRefreshProjectStep(@Qualifier("userWhoDoesNotGetStickerYesterdayReader") JpaPagingItemReader<User> reader,
                                                                    @Qualifier("userEntityToUserIdStrProcessor") ItemProcessor<User, String> processor,
                                                                    @Qualifier("userIdWhoDoesNotGetStickerYesterdayPublisher") ItemWriter<String> writer) {
        return new StepBuilder("UserWhoDoseNotGetStickerYesterdayRefreshProjectStep", jobRepository)
                .<User, String>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
