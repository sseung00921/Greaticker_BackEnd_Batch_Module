package com.example.greaticker.batch.step.processor;

import com.example.greaticker.batch.model.user.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseItemProcessorConfig {

    @Bean(name = "userEntityToUserIdStrProcessor")
    public ItemProcessor<User, String> userEntityToUserIdStrProcessor() {
        return item -> String.valueOf(item.getId());
    }

}
