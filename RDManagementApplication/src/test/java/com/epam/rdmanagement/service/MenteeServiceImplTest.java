package com.epam.rdmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

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

import com.epam.rdmanagement.entity.FeedbackEntity;
import com.epam.rdmanagement.entity.MenteeEntity;
import com.epam.rdmanagement.entity.MentorEntity;
import com.epam.rdmanagement.entity.TrainerEntity;
import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.exception.MenteeNotFoundException;
import com.epam.rdmanagement.exception.MentorNotAvailableException;
import com.epam.rdmanagement.model.FeedbackAddModel;
import com.epam.rdmanagement.model.MenteeModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.repository.MenteeRepository;
import com.epam.rdmanagement.repository.StudentRepository;
import com.epam.rdmanagement.util.ConstantsTestUtil;

/**
 * 
 * @author Aishwarya_Bommisetty
 *
 */
@RunWith(SpringRunner.class)
@PropertySource("classpath:application-test.properties")
public class MenteeServiceImplTest {

	@Value("${email}")
	private String email;

	@Value("${invalid-email1}")
	private String invalidEmail;

	@Value("${invalid-email0}")
	private String invalidEmail0;

	@Value("${first-name}")
	private String firstName;

	@Value("${invalid-first-name}")
	private String invalidFirstName;

	@Value("${last-name}")
	private String lastName;

	@Value("${invalid-last-name}")
	private String invalidLastName;

	@Value("${password}")
	private String password;

	@TestConfiguration
	static class MenteeServiceImplTestContextConfiguration {
		@Bean
		public MenteeService menteeService() {
			return new MenteeServiceImpl();
		}
	}

	@Autowired
	private MenteeService menteeService;

	@MockBean
	private MenteeRepository menteeRepository;

	@MockBean
	private StudentRepository studentRepository;

	MenteeEntity mentee;
	MentorEntity mentor;
	TrainerEntity trainer;
	MenteeEntity mentee1;
	FeedbackEntity feedback;
	UserProfileModel model = new UserProfileModel();
	List<FeedbackEntity> recievedFeedbacks = new ArrayList<>();

	/**
	 * 
	 * @throws MenteeNotFoundException
	 */
	@Before
	public void setUp() {

		mentee = new MenteeEntity();
		mentor = new MentorEntity();
		trainer = new TrainerEntity();
		mentee.setFirstName(firstName);
		mentee.setLastName(lastName);
		mentee.setPassword(password);
		mentee.setEmail(email);
		feedback = new FeedbackEntity();
		feedback.setFromUser(new UserEntity());
		feedback.setToUser(new UserEntity());
		feedback.setFeedback(ConstantsTestUtil.FEEDBACKCONTENT);
		recievedFeedbacks.add(feedback);
		mentee.setRecievedFeedbacks(recievedFeedbacks);
		mentor.addMentee(mentee);
		mentee.setTrainer(trainer);
		mentee1 = new MenteeEntity();
		mentee1.setEmail(invalidEmail0);
		mentee1.setMentor(null);
		Mockito.when(menteeRepository.findByEmail(email)).thenReturn(mentee);
		Mockito.when(menteeRepository.findByEmail(invalidEmail0)).thenReturn(mentee1);

		model.setEmail(mentor.getEmail());
		model.setFirstName(mentor.getFirstName());
		model.setLastName(mentor.getLastName());
	}

	@Test
	public void testGetMentor() throws MentorNotAvailableException {
		UserProfileModel result = menteeService.getMentor(email);
		assertThat(result.getFirstName()).isEqualTo(model.getFirstName());
	}

	@Test
	public void testNullMentor() throws MentorNotAvailableException {

		try {
			menteeService.getMentor(invalidEmail0);
		} catch (MentorNotAvailableException e) {
			assertThat(e.getMessage().equals(ConstantsTestUtil.MENTORNOTAVAILABLEEXCEPTIONMESSAGE));
		}
	}

	@Test
	public void testGetMenteeByEmail() throws MenteeNotFoundException {
		MenteeModel result = menteeService.getMenteeByEmail(email);
		assertThat(result.getFirstName()).isEqualTo(firstName);
	}

	@Test
	public void testMenteeNotFoundForGetMentee() throws MenteeNotFoundException {
		try {
			menteeService.getMenteeByEmail(invalidEmail);
		} catch (MenteeNotFoundException e) {
			assertThat(e.getMessage().equals(ConstantsTestUtil.MENTEENOTFOUNDEXCEPTIONMESSAGE));
		}
	}

	@Test
	public void testGetProfile() throws MenteeNotFoundException {
		MenteeModel expected = new MenteeModel();
		expected.setEmail(email);
		List<FeedbackAddModel> expectedFeedback = new ArrayList<>();
		FeedbackAddModel feed = new FeedbackAddModel(feedback);
		expectedFeedback.add(feed);
		expected.setFeedback(expectedFeedback);
		MenteeModel result = menteeService.getProfile(email);
		assertThat(expected.getEmail()).isEqualTo(result.getEmail());
		assertThat(expected.getFeedback().get(0).getFeedback()).isEqualTo(result.getFeedback().get(0).getFeedback());
	}
}