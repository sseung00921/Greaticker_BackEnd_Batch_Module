package com.example.greaticker.batch.reader;

import com.example.greaticker.batch.model.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringBatchTest
@ActiveProfiles("test")
public class UserWhoDoesNotGetStickerYesterdayReaderTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;


    @Autowired
    @Qualifier("userWhoDoesNotGetStickerYesterdayReader")
    private JpaPagingItemReader<User> userWhoDoesNotGetStickerYesterdayReader;

    @BeforeEach
    public void setUp() {
        // EntityManager 수동으로 생성
        entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        // 트랜잭션 시작
        transaction.begin();

        // Creating User1
        User user1 = new User();
        user1.setNickname("User1");
        user1.setAuthId("AuthID1");
        user1.setAuthEmail("user1@example.com");
        user1.setStickerInventory("[]");
        user1.setHitFavoriteList("[]");
        user1.setLastGet(LocalDateTime.now().minusDays(3));
        user1.setNowProjectId(null);
        user1.setCreatedDateTime(LocalDateTime.of(2024, 9, 3, 10, 0, 0));
        user1.setUpdatedDateTime(LocalDateTime.of(2024, 9, 3, 10, 0, 0));
        entityManager.persist(user1);

        // Creating User2
        User user2 = new User();
        user2.setNickname("User2");
        user2.setAuthId("AuthID2");
        user2.setAuthEmail("user2@example.com");
        user2.setStickerInventory("[]");
        user2.setHitFavoriteList("[]");
        user2.setLastGet(LocalDateTime.now().minusDays(2));
        user2.setNowProjectId(null);
        user2.setCreatedDateTime(LocalDateTime.of(2024, 9, 3, 10, 0, 1));
        user2.setUpdatedDateTime(LocalDateTime.of(2024, 9, 3, 10, 0, 1));
        entityManager.persist(user2);

        // Creating User3
        User user3 = new User();
        user3.setNickname("User3");
        user3.setAuthId("AuthID3");
        user3.setAuthEmail("user3@example.com");
        user3.setStickerInventory("[]");
        user3.setHitFavoriteList("[]");
        user3.setLastGet(LocalDateTime.now().minusDays(1));
        user3.setNowProjectId(null);
        user3.setCreatedDateTime(LocalDateTime.of(2024, 9, 3, 10, 0, 0));
        user3.setUpdatedDateTime(LocalDateTime.of(2024, 9, 3, 10, 0, 0));
        entityManager.persist(user3);

        // 커밋
        transaction.commit();
    }

    @Test
    public void testUserWhoDoesNotGetStickerYesterdayReader() throws Exception {
        //Arrange
        ExecutionContext executionContext = new ExecutionContext();
        userWhoDoesNotGetStickerYesterdayReader.open(executionContext);

        //Act
        List<User> users = new ArrayList<>();
        User user;

        while ((user = userWhoDoesNotGetStickerYesterdayReader.read()) != null) {
            users.add(user);
        }

        //Assert
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getNickname()).isEqualTo("User1");
        assertThat(users.get(1).getNickname()).isEqualTo("User2");

        userWhoDoesNotGetStickerYesterdayReader.close();
    }
}