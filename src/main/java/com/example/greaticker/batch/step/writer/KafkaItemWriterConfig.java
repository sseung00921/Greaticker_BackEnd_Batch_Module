package com.example.greaticker.batch.step.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import static com.example.greaticker.batch.constants.KafkaTopicNames.USERS_TO_REFRESH_PRJOECT;

@Configuration
public class KafkaItemWriterConfig {

    @Bean(name = "userIdWhoDoesNotGetStickerYesterdayPublisher")
    public ItemWriter<String> userIdWhoDoesNotGetStickerYesterdayPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        return items -> {
            for (String item : items) {
                kafkaTemplate.send(USERS_TO_REFRESH_PRJOECT, item);
            }
        };
    }

}
