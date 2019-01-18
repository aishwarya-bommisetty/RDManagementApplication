package com.epam.rdmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.rdmanagement.entity.MenteeEntity;
import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.entity.StudentEntity;
import com.epam.rdmanagement.entity.TrainerEntity;
import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.exception.AccessDeniedException;
import com.epam.rdmanagement.exception.MentorNotAvailableException;
import com.epam.rdmanagement.exception.StudentNotFoundException;
import com.epam.rdmanagement.model.TrainerModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.repository.FeedbackRepository;
import com.epam.rdmanagement.repository.TrainerRepository;
import com.epam.rdmanagement.repository.UserRepository;

/**
 * test cases for trainer services.
 * 
 * @author Sourabh
 *
 */
@RunWith(SpringRunner.class)
public class TrainerServiceTestCase {

	@TestConfiguration
	static class TrainerServiceImplTestContextConfiguration {

		@Bean
		public TrainerService trainerService() {
			return new TrainerServiceImpl();
		}
	}

	@Autowired
	private TrainerService trainerService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private TrainerRepository trainerRepository;

	@MockBean
	private FeedbackRepository feedbackRepository;

	private MenteeEntity mentee;
	private RoleEntity student, trainer;
	private List<StudentEntity> studentList;
	private TrainerEntity venu;
	private StudentEntity patil;
	private UserEntity user;
	private RoleEntity role;
	private List<UserEntity> mentorList;
	private List<TrainerEntity> trainerList;
	private UserProfileModel userProfileModel;
	private List<UserProfileModel> userProfileModels;
	private DozerBeanMapper beanMapper;
	private UserProfileModel studentProfileModel;
	private List<UserProfileModel> studentProfileModels;
	private TrainerModel trainerModel;

	@Value("${trainer.role-name}")
	private String trainerRoleName;

	@Value("${mentor.role-name}")
	private String mentorRoleName;

	@Value("${student.role-name}")
	private String studentRoleName;

	@Value("${mentor.email}")
	private String mentorEmail;

	@Value("${mentor.first-name}")
	private String mentorFirstName;

	@Value("${mentor.last-name}")
	private String mentorLastName;

	@Value("${password}")
	private String password;

	@Value("${trainer.email}")
	private String trainerEmail;

	@Value("${trainer.first-name}")
	private String trainerFirstName;

	@Value("${trainer.last-name}")
	private String trainerLastName;

	@Value("${email}")
	private String email;

	@Value("${first-name}")
	private String firstName;

	@Value("${last-name}")
	private String lastName;

	@Before
	public void setUp() {
		role = new RoleEntity();
		role.setRoleName(mentorRoleName);

		student = new RoleEntity();
		student.setRoleName(studentRoleName);

		trainer = new RoleEntity();
		trainer.setRoleName(trainerRoleName);

		user = new UserEntity();
		user.setEmail(mentorEmail);
		user.setFirstName(mentorFirstName);
		user.setLastName(mentorLastName);
		user.setRole(role);

		mentorList = new ArrayList<>();
		mentorList.add(user);

		mentee = new MenteeEntity();
		mentee.setFirstName(firstName);

		patil = new StudentEntity();
		patil.setEmail(email);
		patil.setFirstName(firstName);
		patil.setLastName(lastName);
		patil.setRole(student);

		studentList = new ArrayList<>();
		studentList.add(patil);

		venu = new TrainerEntity();
		venu.setEmail(trainerEmail);
		venu.setFirstName(trainerFirstName);
		venu.setLastName(trainerLastName);
		venu.setStudents(studentList);

		trainerList = new ArrayList<>();
		trainerList.add(venu);

		patil.setTrainer(venu);
		venu.setRole(trainer);
		mentee.setTrainer(venu);

		userProfileModel = new UserProfileModel();
		userProfileModel.setEmail(mentorEmail);
		userProfileModel.setFirstName(mentorFirstName);
		userProfileModel.setLastName(mentorLastName);

		beanMapper = new DozerBeanMapper();
		trainerModel = beanMapper.map(venu, TrainerModel.class);

		userProfileModels = new ArrayList<>();
		userProfileModels.add(userProfileModel);

		studentProfileModel = new UserProfileModel();
		studentProfileModel.setEmail(email);
		studentProfileModel.setFirstName(firstName);
		studentProfileModel.setLastName(lastName);

		studentProfileModels = new ArrayList<>();
		studentProfileModels.add(studentProfileModel);

	}

	/**
	 * test case to check list of mentors.
	 * 
	 * @throws AccessDeniedException
	 * @throws MentorNotAvailableException
	 */
	@Test
	public void testGetAllMentorResult() throws MentorNotAvailableException {
		Mockito.when(userRepository.findByRoleRoleName(mentorRoleName)).thenReturn(mentorList);
		List<UserProfileModel> mentors = trainerService.getAllMentors();
		assertThat(mentors.get(0).getEmail()).isEqualTo(userProfileModels.get(0).getEmail());
	}

	/**
	 * test case to check if mentor present
	 * 
	 * @throws AccessDeniedException
	 * @throws MentorNotAvailableException
	 */
	@Test(expected = MentorNotAvailableException.class)
	public void testMentorPresent() throws MentorNotAvailableException {
		mentorList.clear();
		Mockito.when(userRepository.findByRoleRoleName(mentorRoleName)).thenReturn(mentorList);
		trainerService.getAllMentors();
	}

	/**
	 * test case to check list of student.
	 * 
	 * @throws AccessDeniedException
	 * @throws StudentNotFoundException
	 */
	@Test
	public void testgetAllStudentsUnderTrainer() throws StudentNotFoundException {
		Mockito.when(trainerRepository.findByEmail(trainerEmail)).thenReturn(venu);
		List<UserProfileModel> students = trainerService.getAllStudentsUnderTrainer(trainerEmail);
		assertThat(students.get(0).getEmail()).isEqualTo(studentProfileModels.get(0).getEmail());
	}

	/**
	 * test case to check if Student present
	 * 
	 * @throws AccessDeniedException
	 * @throws MentorNotAvailableException
	 * @throws StudentNotFoundException
	 */
	@Test(expected = StudentNotFoundException.class)
	public void testStudentPresent() throws StudentNotFoundException {
		studentList.clear();
		TrainerEntity trainer = mock(TrainerEntity.class);
		Mockito.when(trainerRepository.findByEmail(trainerEmail)).thenReturn(trainer);
		Mockito.when(trainer.getStudents()).thenReturn(studentList);
		trainerService.getAllStudentsUnderTrainer(trainerEmail);
	}

	@Test
	public void testGetProfile() {
		Mockito.when(trainerRepository.findByEmail(trainerEmail)).thenReturn(venu);
		TrainerModel resultUserModel = trainerService.getProfile(trainerEmail);
		assertThat(resultUserModel).isEqualTo(trainerModel);
	}
}
