package com.epam.rdmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.rdmanagement.entity.FeedbackEntity;
import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.exception.OperationCouldNotBeProcessed;
import com.epam.rdmanagement.model.FeedbackAddModel;
import com.epam.rdmanagement.model.RoleModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.repository.FeedbackRepository;
import com.epam.rdmanagement.repository.UserRepository;

/**
 * 
 * @author Prabhudeep_Banga
 *
 */
@RunWith(SpringRunner.class)
public class ProfileServiceTestCase {

    /**
     * Test Configuration.
     * 
     * @author Prabhudeep_Banga
     *
     */
    @TestConfiguration
    static class ProfileServiceImplTestContextConfiguration {

        /**
         * create trainer service implementation object.
         * 
         * @return trainer service implementation object.
         */
        @Bean
        public ProfileService profileService() {
            return new ProfileServiceImpl();
        }
    }

    @Autowired
    private ProfileService profileService;

    @MockBean
    private UserRepository userRepository;
    
    @MockBean
    private FeedbackRepository feedbackRepository;

    private UserEntity userEntity;

    private UserEntity fromUser;

    private UserEntity toUser;

    private UserProfileModel userProfileModel;

    private RoleEntity roleEntity;

    private RoleModel roleModel;
    
    private FeedbackAddModel feedbackAddModel;
    
    private FeedbackEntity feedbackEntity;

    private DozerBeanMapper beanMapper;

    @Value("${role.trainer}")
    private String roleTrainer;

    @Value("${trainer.email}")
    private String trainerEmail;

    @Value("${trainer.first-name}")
    private String trainerFirstName;

    @Value("${trainer.last-name}")
    private String trainerLastName;

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
    private String feedback;
        
    /**
     * Set up the values before test run.
     */
    @Before
    public void setUp() {

        roleEntity = new RoleEntity();
        userEntity = new UserEntity();
        fromUser = new UserEntity();
        toUser = new UserEntity();
        feedbackAddModel = new FeedbackAddModel();
        feedbackEntity = new FeedbackEntity();
        beanMapper = new DozerBeanMapper();
        
        roleEntity.setRoleName(roleTrainer);

        roleModel = beanMapper.map(roleEntity, RoleModel.class);

        userEntity.setEmail(trainerEmail);
        userEntity.setFirstName(trainerFirstName);
        userEntity.setLastName(trainerLastName);
        userEntity.setRole(roleEntity);
        
        fromUser.setEmail(fromUserEmail);
        fromUser.setFirstName(fromUserFirstName);
        fromUser.setLastName(fromUserFirstName);
        
        toUser.setEmail(toUserEmail);
        toUser.setFirstName(toUserFirstName);
        toUser.setLastName(toUserFirstName);
        
        feedbackAddModel.setFromUserEmail(fromUserEmail);
        feedbackAddModel.setToUserEmail(toUserEmail);
        feedbackAddModel.setFeedback(feedback);

        feedbackEntity.setFromUser(fromUser);
        feedbackEntity.setToUser(toUser);
        feedbackEntity.setFeedback(feedbackAddModel.getFeedback());
        
        userProfileModel = beanMapper.map(userEntity, UserProfileModel.class);

        Mockito.when(userRepository.findByEmail(trainerEmail)).thenReturn(userEntity);
        Mockito.when(userRepository.findByEmail(fromUserEmail)).thenReturn(fromUser);
        Mockito.when(userRepository.findByEmail(toUserEmail)).thenReturn(toUser);
        Mockito.when(feedbackRepository.save(feedbackEntity)).thenReturn(feedbackEntity);
        Mockito.when(feedbackRepository.findByFromUserAndToUser(fromUser, toUser)).thenReturn(feedbackEntity);
    }

    /**
     * Test to check retrieval of role.
     */
    @Test
    public void testGetRoleResult() {

        RoleModel resultModel = profileService.getRole(trainerEmail);
        assertThat(resultModel).isEqualTo(roleModel);
    }

    @Test
    public void testAddfeedback() throws OperationCouldNotBeProcessed {
        
        boolean result = profileService.giveFeedback(feedbackAddModel);
        assertThat(result).isEqualTo(true);
    }
}
