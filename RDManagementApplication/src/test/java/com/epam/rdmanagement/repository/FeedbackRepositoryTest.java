package com.epam.rdmanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.rdmanagement.entity.FeedbackEntity;
import com.epam.rdmanagement.entity.UserEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FeedbackRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FeedbackRepository feedbackRepository;

    private FeedbackEntity feedbackEntity;

    private UserEntity fromUser;

    private UserEntity toUser;

    @Value("${fromuser.email}")
    private String fromUserEmail;

    @Value("${fromuser.first-name}")
    private String fromUserFirstName;

    @Value("${fromuser.last-name}")
    private String fromUserLastName;

    @Value("${touser.email}")
    private String toUserEmail;

    @Value("${touser.first-name}")
    private String toUserFirstName;

    @Value("${touser.last-name}")
    private String toUserLastName;
    
    @Value("${feedback}")
    private String testFeedbackString;
    
    /**
     * SetUp for tests
     */
    @Before
    public void SetUp() {

        feedbackEntity = new FeedbackEntity();
        fromUser = new UserEntity();
        toUser = new UserEntity();

        fromUser.setEmail(fromUserEmail);
        fromUser.setFirstName(fromUserFirstName);
        fromUser.setLastName(fromUserFirstName);

        toUser.setEmail(toUserEmail);
        toUser.setFirstName(toUserFirstName);
        toUser.setLastName(toUserFirstName);

        feedbackEntity.setFromUser(fromUser);
        feedbackEntity.setToUser(toUser);
        feedbackEntity.setFeedback(testFeedbackString);

        entityManager.persist(fromUser);
        entityManager.persist(toUser);
        entityManager.persist(feedbackEntity);
        entityManager.flush();
    }

    @Test
    public void TestFindByFromUserAndToUser() {
        
        FeedbackEntity resultEntity = feedbackRepository.findByFromUserAndToUser(fromUser, toUser);
        assertThat(resultEntity).isEqualTo(feedbackEntity);
    }
}
