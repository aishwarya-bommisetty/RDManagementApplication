package com.epam.rdmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.rdmanagement.entity.MenteeEntity;
import com.epam.rdmanagement.entity.MentorEntity;
import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.exception.MenteesNotFoundException;
import com.epam.rdmanagement.model.MenteeModel;
import com.epam.rdmanagement.model.MentorModel;
import com.epam.rdmanagement.repository.MentorRepository;
import com.epam.rdmanagement.repository.UserRepository;

/**
 * The Class MentorServiceTest.
 *
 * @author Prabhudeep_Banga
 */
@RunWith(SpringRunner.class)
@PropertySource("classpath: application-test.properties")
public class MentorServiceTest {

    @TestConfiguration
    static class MentorServiceImplTestContextConfiguration {

        /**
         * creates object Mentor service implementation.
         *
         * @return mentorEntity service implementation object.
         */
        @Bean
        public MentorService mentorService() {

            return new MentorServiceImpl();
        }
    }

    @Autowired
    MentorService mentorService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    MentorRepository mentorRepository;

    private MentorEntity mentorEntity;

    private MentorEntity mentorWithNoMentees;

    private MenteeModel menteeModel;

    private MentorModel mentorModel;
    
    private MenteeEntity menteeEntity;

    private RoleEntity mentorRole;

    private RoleEntity menteeRole;

    List<MenteeModel> menteesModelList;

    List<MenteeEntity> menteesEntityList;

    DozerBeanMapper beanMapper;

    @Value("${mentorEntity.email}")
    private String mentorEmail;

    @Value("${mentorEntity.first-name}")
    private String mentorFirstName;

    @Value("${mentorEntity.last-name}")
    private String mentorLastName;

    @Value("${mentee.email}")
    private String menteeEmail;

    @Value("${mentee.first-name}")
    private String menteeFirstName;

    @Value("${mentee.last-name}")
    private String menteeLastName;

    @Value("${role.mentor}")
    private String roleMentor;

    @Value("${role.mentee}")
    private String roleMentee;

    @Value("${dummy-mentorEntity.email}")
    private String dummyMentorEmail;

    
    @Value("${dummy-mentorEntity.username}")
    private String dummyMentorUserName;
    
    /**
     * Set up the values before running the test.
     */
    @Before
    public void setUp() {

        menteeModel = new MenteeModel();
        mentorModel = new MentorModel();
        mentorEntity = new MentorEntity();
        menteeRole = new RoleEntity();
        mentorRole = new RoleEntity();
        menteesModelList = new ArrayList<MenteeModel>();
        menteesEntityList = new ArrayList<MenteeEntity>();
        beanMapper = new DozerBeanMapper();

        menteeRole.setRoleName(roleMentee);
        mentorRole.setRoleName(roleMentor);

        menteeModel.setEmail(menteeEmail);
        menteeModel.setFirstName(menteeFirstName);
        menteeModel.setLastName(menteeLastName);

        menteesModelList.add(menteeModel);

        menteeEntity = beanMapper.map(menteeModel, MenteeEntity.class);
        menteesEntityList.add(menteeEntity);

        mentorEntity.setEmail(mentorEmail);
        mentorEntity.setFirstName(mentorFirstName);
        mentorEntity.setLastName(mentorLastName);
        mentorEntity.setRole(mentorRole);
        mentorEntity.setMentees(menteesEntityList);
        
        mentorModel = beanMapper.map(mentorEntity, MentorModel.class);

        mentorWithNoMentees = Mockito.mock(MentorEntity.class);
        Mockito.when(mentorRepository.findByEmail(mentorEmail)).thenReturn(mentorEntity);
        Mockito.when(mentorRepository.findByEmail(dummyMentorEmail)).thenReturn(mentorWithNoMentees);

    }

    /**
     * Test for getting all mentees under mentorEntity.
     * 
     * @throws MenteesNotFoundException
     */
    @Test
    public void testGetAllMenteesUnderMentor() throws MenteesNotFoundException {

        List<MenteeModel> mentees = mentorService.getAllMenteesUnderMentor(mentorEmail);
        assertThat(mentees).isEqualTo(menteesModelList);

    }

    /**
     * Test for no mentees found for a mentorEntity.
     *
     * @throws MenteesNotFoundException
     * 
     */
    @Test(expected = MenteesNotFoundException.class)
    public void testForNoMentessFound() throws MenteesNotFoundException {

        mentorService.getAllMenteesUnderMentor(dummyMentorEmail);

    }

    /**
     * Test to check retrieval of profile.
     */
    @Test
    public void testGetProfile() {

        MentorModel resultMentorModel = mentorService.getProfile(mentorEmail);
        assertThat(resultMentorModel).isEqualTo(mentorModel);
    }

}
