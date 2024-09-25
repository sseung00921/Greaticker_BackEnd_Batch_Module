package com.example.greaticker.batch.step.reader;

import com.example.greaticker.batch.model.user.User;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

@Configuration
public class DatabaseItemReaderConfig {

    @Bean(name = "userWhoDoesNotGetStickerYesterdayReader")
    @StepScope
    public JpaPagingItemReader<User> userWhoDoesNotGetStickerYesterdayReader(EntityManagerFactory entityManagerFactory) {
        String jpqlQuery = "SELECT u FROM User u WHERE u.lastGet < :date";

        JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();
        reader.setQueryString(jpqlQuery);
        reader.setEntityManagerFactory(entityManagerFactory);
        LocalDate today = LocalDateTime.now().toLocalDate();
        System.out.println(today + " inReadConfigToday");
        System.out.println(today.minusDays(1).atStartOfDay() + " inReadConfigResetBaseTime");
        reader.setParameterValues(Collections.singletonMap("date", today.minusDays(1).atStartOfDay()));
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime resetBaseTimeInKR = now.minusDays(1).minusMinutes(1).plusHours(9); //kr기준이라서 9시간을 더해줌.
//        reader.setParameterValues(Collections.singletonMap("date", resetBaseTimeInKR));
        reader.setPageSize(10);
        reader.setSaveState(true);
        try {
            reader.afterPropertiesSet();
        } catch (Exception e) {
            System.out.println("Initialization failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize MyCustomReader", e);
        }
        return reader;
    }

}
