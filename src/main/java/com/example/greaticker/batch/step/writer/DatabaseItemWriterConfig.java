package com.example.greaticker.batch.step.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component  // Spring이 이 클래스를 빈으로 관리할 수 있도록 선언
public class DatabaseItemWriterConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean(name = "userIdWhoDoesNotGetStickerYesterdayPublisher")
    public ItemWriter<String> userIdWhoDoesNotGetStickerYesterdayPublisher() {
        return items -> {
            for (String item : items) {
                // u.nowProjectId가 item일 때, 프로젝트 상태를 'RESET'으로 업데이트
                updateProjectStateToReset(item);
            }
        };
    }

    @Transactional
    public void updateProjectStateToReset(String nowProjectId) {
        String jpqlQuery = "UPDATE Project p SET p.state = 'RESET' " +
                "WHERE p.id = :nowProjectId";

        entityManager.createQuery(jpqlQuery)
                .setParameter("nowProjectId", nowProjectId)
                .executeUpdate();
    }
}