package com.example.greaticker.batch.step.reader;

import com.example.greaticker.batch.model.user.User;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Collections;

@Configuration
public class DatabaseItemReaderConfig {

    @Bean(name = "userWhoDoesNotGetStickerYesterdayReader")
    public JpaPagingItemReader<User> userWhoDoesNotGetStickerYesterdayReader(EntityManagerFactory entityManagerFactory) {
        String jpqlQuery = "SELECT u FROM User u WHERE u.lastGet <= :date";

        JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();
        reader.setQueryString(jpqlQuery);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setParameterValues(Collections.singletonMap("date", LocalDateTime.now().minusDays(2)));
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
