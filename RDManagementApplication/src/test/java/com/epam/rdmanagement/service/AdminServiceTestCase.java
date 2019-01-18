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
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.rdmanagement.entity.MentorEntity;
import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.entity.StudentEntity;
import com.epam.rdmanagement.entity.TrainerEntity;
import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.exception.OperationCouldNotBeProcessed;
import com.epam.rdmanagement.model.UserAddModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.repository.FeedbackRepository;
import com.epam.rdmanagement.repository.MentorRepository;
import com.epam.rdmanagement.repository.StudentRepository;
import com.epam.rdmanagement.repository.TrainerRepository;
import com.epam.rdmanagement.repository.UserRepository;

/**
 * test cases for admin services.
 * 
 * @author Sourabh
 *
 */
@RunWith(SpringRunner.class)
public class AdminServiceTestCase {

	@TestConfiguration
	static class AdminServiceImplTestContextConfiguration {

		@Bean
		public AdminService AdminService() {
			return new AdminServiceImpl();
		}
	}

	@Autowired
	private AdminService adminService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private TrainerRepository trainerRepository;

	@MockBean
	private MentorRepository mentorRepository;

	@MockBean
	private StudentRepository studentRepository;

	@MockBean
	private FeedbackRepository feedbackRepository;

	private RoleEntity trainer, mentor, student;
	private UserProfileModel ravi;
	private UserProfileModel ajay;
	private UserAddModel userAddModel;
	private UserAddModel ajayAddModel;
	private TrainerEntity venu;
	private MentorEntity ajayMentor;
	private RoleEntity roleEntity;
	private StudentEntity raviStudent;
	private List<StudentEntity> studentList;
	private List<MentorEntity> mentorList;
	private List<TrainerEntity> trainerList;
	private List<UserProfileModel> expectedResult;
	private UserProfileModel userProfileModel;
	private List<UserProfileModel> mentorUserProfileList;
	private List<UserProfileModel> studentUserProfileList;
	private UserEntity userEntity;
	private UserProfileModel adminProfileModel;
	private DozerBeanMapper beanMapper;

	@Value("${trainer-added.message}")
	private String trainerAddedSuccessfullyMessage;

	@Value("${mentor-added.message}")
	private String mentorAddedSuccessfullyMessage;

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
		trainer = new RoleEntity();
		trainer.setRoleName(trainerRoleName);

		mentor = new RoleEntity();
		mentor.setRoleName("mentor");

		student = new RoleEntity();
		student.setRoleName(studentRoleName);

		ravi = new UserProfileModel();
		ravi.setEmail(email);
		ravi.setFirstName(firstName);
		ravi.setLastName(lastName);

		roleEntity = new RoleEntity();
		roleEntity.setRoleName(trainerRoleName);

		userEntity = new UserEntity();
		userEntity.setEmail(trainerEmail);
		userEntity.setFirstName(trainerFirstName);
		userEntity.setLastName(trainerLastName);
		userEntity.setRole(roleEntity);

		raviStudent = new StudentEntity();
		raviStudent.setEmail(email);
		raviStudent.setFirstName(firstName);
		raviStudent.setLastName(lastName);

		ajayMentor = new MentorEntity();
		ajayMentor.setEmail(mentorEmail);
		ajayMentor.setFirstName(mentorFirstName);
		ajayMentor.setLastName(mentorLastName);
		ajayMentor.setRole(mentor);

		ajay = new UserProfileModel();
		ajay.setEmail(mentorEmail);
		ajay.setFirstName(mentorFirstName);
		ajay.setLastName(mentorLastName);

		venu = new TrainerEntity();
		venu.setEmail(trainerEmail);
		venu.setFirstName(trainerFirstName);
		venu.setLastName(trainerLastName);
		venu.setRole(trainer);

		userProfileModel = new UserProfileModel();
		userProfileModel.setEmail(trainerEmail);
		userProfileModel.setFirstName(trainerFirstName);
		userProfileModel.setLastName(trainerLastName);

		beanMapper = new DozerBeanMapper();
		adminProfileModel = beanMapper.map(userEntity, UserProfileModel.class);

		userAddModel = new UserAddModel();
		userAddModel.setEmail(trainerEmail);
		userAddModel.setFirstName(trainerFirstName);
		userAddModel.setLastName(trainerLastName);
		userAddModel.setPassword(password);
		userAddModel.setRoleId(3);

		ajayAddModel = new UserAddModel();
		ajayAddModel.setEmail(mentorEmail);
		ajayAddModel.setFirstName(mentorFirstName);
		ajayAddModel.setLastName(mentorLastName);
		ajayAddModel.setPassword(password);
		ajayAddModel.setRoleId(4);

		trainerList = new ArrayList<>();
		mentorList = new ArrayList<>();
		studentList = new ArrayList<>();
		expectedResult = new ArrayList<>();
		mentorUserProfileList = new ArrayList<>();
		studentUserProfileList = new ArrayList<>();

		trainerList.add(venu);
		mentorList.add(ajayMentor);
		studentList.add(raviStudent);
		expectedResult.add(userProfileModel);
		mentorUserProfileList.add(ajay);
		studentUserProfileList.add(ravi);
	}

	/**
	 * test case to check success of add mentor.
	 * 
	 * @throws @throws OperationCouldNotBeProcessed
	 */
	@Test
	public void testAddMentorSucceed() throws OperationCouldNotBeProcessed {
		Mockito.when(mentorRepository.findByEmail(ajayMentor.getEmail())).thenReturn(ajayMentor);
		Mockito.when(mentorRepository.save(ajayMentor)).thenReturn(ajayMentor);
		String successResult = adminService.addMentor(ajayAddModel);
		assertThat(successResult).isEqualTo(mentorAddedSuccessfullyMessage);
	}

	/**
	 * test case to check success of add trainer.
	 * 
	 * @throws @throws OperationCouldNotBeProcessed
	 */
	@Test
	public void testAddTrainerSucceed() throws OperationCouldNotBeProcessed {
		Mockito.when(trainerRepository.findByEmail(venu.getEmail())).thenReturn(venu);
		Mockito.when(trainerRepository.save(venu)).thenReturn(venu);
		String successResult = adminService.addTrainer(userAddModel);
		assertThat(successResult).isEqualTo(trainerAddedSuccessfullyMessage);
	}

	/**
	 * test case to check failure of add trainer.
	 * 
	 * @throws @throws OperationCouldNotBeProcessed
	 */
	@Test(expected = OperationCouldNotBeProcessed.class)
	public void testAddTrainerFailed() throws OperationCouldNotBeProcessed {
		Mockito.when(trainerRepository.save(venu)).thenReturn(null);
		adminService.addTrainer(userAddModel);
	}

	/**
	 * test case to check failure of add trainer.
	 * 
	 * @throws @throws OperationCouldNotBeProcessed
	 */
	@Test(expected = OperationCouldNotBeProcessed.class)
	public void testAddMentorFailed() throws OperationCouldNotBeProcessed {
		Mockito.when(userRepository.save(ajayMentor)).thenReturn(null);
		adminService.addMentor(ajayAddModel);
	}

	/**
	 * test case to get all mentors.
	 * 
	 * @throws 
	 */
	@Test
	public void testGetAllMentorsResult()  {
		Mockito.when(mentorRepository.findAll()).thenReturn(mentorList);
		List<UserProfileModel> actualResult = adminService.getAllMentors();
		assertThat(actualResult.get(0).getEmail()).isEqualTo(mentorUserProfileList.get(0).getEmail());
	}

	/**
	 * test case to get all student.
	 * 
	 * @throws
	 */
	@Test
	public void testGetAllStudentsResult() {
		Mockito.when(studentRepository.findAll()).thenReturn(studentList);
		List<UserProfileModel> actualResult = adminService.getAllStudents();
		assertThat(actualResult.get(0).getEmail()).isEqualTo(studentUserProfileList.get(0).getEmail());
	}

	/**
	 * test case to get all trainers.
	 * 
	 * @throws
	 */
	@Test
	public void testGetAllTrainersResult() {
		Mockito.when(trainerRepository.findAll()).thenReturn(trainerList);
		List<UserProfileModel> actualResult = adminService.getAllTrainers();
		assertThat(actualResult.get(0).getEmail()).isEqualTo(expectedResult.get(0).getEmail());
	}

	/**
	 * test case to check result of get profile method.
	 */
	@Test
	public void testGetProfile() {
		Mockito.when(userRepository.findByEmail(email)).thenReturn(userEntity);
		UserProfileModel resultUserModel = adminService.getProfile(email);
		assertThat(resultUserModel).isEqualTo(adminProfileModel);
	}

}
