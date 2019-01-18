package com.epam.rdmanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.rdmanagement.model.UserAddModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.service.AdminService;

/**
 * 
 * @author Anand_Edasseril
 *
 *         Test cases to test Admin Controller.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = AdminController.class)
@PropertySource("classpath:application-test.properties")
public class AdminControllerTest {

	@Autowired
	MockMvc mockMvc;

	/**
	 * Mocking Admin service.
	 */
	@MockBean
	private AdminService adminService;
	
    /**
     * URL to get profile of admin.
     */
    @Value("${get-admin-profile.url}")
    private String getAdminProfileURL;
    
    /**
     * Email of the admin.
     */
    @Value("${admin-email}")
    private String adminEmail;

	/**
	 * URL path to add a new trainer.
	 */
	@Value("${add-trainer.url}")
	private String addTrainerURL;

	/**
	 * URL path to add a new mentor.
	 */
	@Value("${add-mentor.url}")
	private String addMentorURL;

	/**
	 * URL path to get all trainers.
	 */
	@Value("${get-all-trainer.url}")
	private String getAllTrainerURL;

	/**
	 * URL path get all mentors.
	 */
	@Value("${get-all-mentor.url}")
	private String getAllMentorURL;

	/**
	 * URL path to get all students.
	 */
	@Value("${get-all-students.url}")
	private String getAllStudentsURL;

	/**
	 * User data in json format.
	 */
	@Value("${user-json}")
	private String jsonUserData;

	/**
	 * Success message after added trainer.
	 */
	@Value("${added-trainer-success.message}")
	private String addedTrainerSuccessMessage;

	/**
	 * Success message after added mentor.
	 */
	@Value("${added-mentor-success.message}")
	private String addedMentorSuccessMessage;

	/**
	 * Json response key for message.
	 */
	@Value("${json-message.key}")
	private String jsonMessageKey;

	/**
	 * json response key for status.
	 */
	@Value("${json-status.key}")
	private String jsonStatusKey;

	/**
	 * json response key for email.
	 */
	@Value("${json-email.key}")
	private String jsonEmailKey;
	
	/**
	 * json response key for email.
	 */
	@Value("${json-user-email.key}")
	private String jsonEmailAdminKey;
	
	/**
	 * User's email id.
	 */
	@Value("${user.email}")
	private String userEmail;

	/**
	 * User's first name.
	 */
	@Value("${user.first-name}")
	private String userFirstName;

	/**
	 * User's last name.
	 */
	@Value("${user.last-name}")
	private String userLastName;

	/**
	 * User's password.
	 */
	@Value("${user.password}")
	private String userPassword;

	/**
	 * Role name for students.
	 */
	@Value("${student.role-name}")
	private String studentRoleName;

	/**
	 * Role name for trainer.
	 */
	@Value("${trainer.role-name}")
	private String trainerRoleName;

	/**
	 * Role name for mentor.
	 */
	@Value("${mentor.role-name}")
	private String mentorRoleName;
	    
    /**
     * Testing the get admin profile .
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "ADMIN")
    public void testGetAdminProfile() throws Exception {

        UserProfileModel user = new UserProfileModel();
        user.setEmail(adminEmail);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);

        when(adminService.getProfile(adminEmail)).thenReturn(user);
        mockMvc.perform(get(getAdminProfileURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonEmailAdminKey, is(adminEmail)));
    }

	/**
	 * Testing the add trainer com.epam.rdmanagement.controller.
	 */
	@Test
	@WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "ADMIN")
	public void testAddTrainer() throws Exception {

		UserAddModel trainer = new UserAddModel();
		trainer.setEmail(userEmail);
		trainer.setFirstName(userFirstName);
		trainer.setLastName(userLastName);
		trainer.setPassword(userPassword);

		String mentorJson = new String(jsonUserData);
		when(adminService.addTrainer(trainer)).thenReturn(addedTrainerSuccessMessage);
		mockMvc.perform(post(addTrainerURL).contentType(MediaType.APPLICATION_JSON).content(mentorJson)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath(jsonStatusKey, Matchers.equalTo(201)))
				.andExpect(jsonPath(jsonMessageKey, Matchers.equalTo(addedTrainerSuccessMessage)));
	}

	/**
	 * Testing the add mentor com.epam.rdmanagement.controller.
	 */
	@Test
	@WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "ADMIN")
	public void testAddMentor() throws Exception {

		UserAddModel mentor = new UserAddModel();
		mentor.setEmail(userEmail);
		mentor.setFirstName(userFirstName);
		mentor.setLastName(userLastName);
		mentor.setPassword(userPassword);

		String mentorJson = new String(jsonUserData);
		when(adminService.addMentor(mentor)).thenReturn(addedMentorSuccessMessage);
		mockMvc.perform(post(addMentorURL).contentType(MediaType.APPLICATION_JSON).content(mentorJson)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath(jsonStatusKey, Matchers.equalTo(201)))
				.andExpect(jsonPath(jsonMessageKey, Matchers.equalTo(addedMentorSuccessMessage)));
	}

	/**
	 * Testing the get all trainer controllers.
	 */
	@Test
	@WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "ADMIN")
	public void testGetAllTrainers() throws Exception {

		UserProfileModel trainer = new UserProfileModel();
		trainer.setEmail(userEmail);
		trainer.setFirstName(userFirstName);
		trainer.setLastName(userLastName);

		List<UserProfileModel> trainers = Arrays.asList(trainer);
		when(adminService.getAllTrainers()).thenReturn(trainers);
		mockMvc.perform(get(getAllTrainerURL)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath(jsonEmailKey, is(userEmail)));
	}

	/**
	 * Testing the get all mentors com.epam.rdmanagement.controller.
	 */
	@Test
	@WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "ADMIN")
	public void testGetAllMentors() throws Exception {

		UserProfileModel mentor = new UserProfileModel();
		mentor.setEmail(userEmail);
		mentor.setFirstName(userFirstName);
		mentor.setLastName(userLastName);

		List<UserProfileModel> mentors = Arrays.asList(mentor);
		when(adminService.getAllMentors()).thenReturn(mentors);
		mockMvc.perform(get(getAllMentorURL)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath(jsonEmailKey, is(userEmail)));
	}

	/**
	 * Testing the get all students.
	 */
	/**
	 * @throws Exception
	 */
	@Test
	@WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "ADMIN")
	public void testGetAllStudents() throws Exception {

		UserProfileModel student = new UserProfileModel();
		student.setEmail(userEmail);
		student.setFirstName(userFirstName);
		student.setLastName(userLastName);

		List<UserProfileModel> students = Arrays.asList(student);
		when(adminService.getAllStudents()).thenReturn(students);
		mockMvc.perform(get(getAllStudentsURL)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath(jsonEmailKey, is(userEmail)));
	}

}
