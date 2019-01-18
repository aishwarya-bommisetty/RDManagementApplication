package com.epam.rdmanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

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

import com.epam.rdmanagement.model.TrainerModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.service.TrainerService;

/**
 * 
 * @author Anand_Edasseril
 * 
 * Test cases for Trainer com.epam.rdmanagement.controller.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = TrainerController.class)
@PropertySource("classpath:application-test.properties")
public class TrainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocking trainer service.
     */
    @MockBean
    private TrainerService trainerService;

    /**
     * URL to get profile of tariner.
     */
    @Value("${get-trainer-profile.url}")
    private String getTrainerProfileURL;
    
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
     * Role name for mentor.
     */
    @Value("${mentor.role-name}")
    private String mentorRoleName;

    /**
     * Role name for students.
     */
    @Value("${student.role-name}")
    private String studentRoleName;

    /**
     * URL path to get all trainees under a trainer.
     */
    @Value("${get-all-students-under-trainer.url}")
    private String getAllStudentsUnderTrainerURL;

    /**
     * URL path to get all mentors for trainer.
     */
    @Value("${get-all-mentors-for-trainer}")
    private String getAllMentorsForTrainerURL;

    /**
     * Name of the trainer.
     */
    @Value("${trainer-email}")
    private String trainerEmail;

    /**
     * json response key for email.
     */
    @Value("${json-email.key}")
    private String jsonEmailKey;
    
    /**
     * json response key for email.
     */
    @Value("${json-user-email.key}")
    private String jsonEmailTrainerKey;

    /**
     * Testing the get trainer profile .
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "TRAINER")
    public void testGetTrainerProfile() throws Exception {

        TrainerModel user = new TrainerModel();
        user.setEmail(trainerEmail);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);

        when(trainerService.getProfile(trainerEmail)).thenReturn(user);
        mockMvc.perform(get(getTrainerProfileURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonEmailTrainerKey, is(trainerEmail)));
    }

    /**
     * Testing the get all students under a trainer com.epam.rdmanagement.controller.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "TRAINER")
    public void testGetAllStudentsUnderTrainer() throws Exception {

        UserProfileModel student = new UserProfileModel();
        student.setEmail(userEmail);
        student.setFirstName(userFirstName);
        student.setLastName(userLastName);

        List<UserProfileModel> students = Arrays.asList(student);
        when(trainerService.getAllStudentsUnderTrainer(trainerEmail)).thenReturn(students);
        mockMvc.perform(get(getAllStudentsUnderTrainerURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonEmailKey, is(userEmail)));
    }

    /**
     * Testing the get all mentors com.epam.rdmanagement.controller.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "TRAINER")
    public void testGetAllMentors() throws Exception {

        UserProfileModel user = new UserProfileModel();
        user.setEmail(userEmail);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);

        List<UserProfileModel> users = Arrays.asList(user);
        when(trainerService.getAllMentors()).thenReturn(users);
        mockMvc.perform(get(getAllMentorsForTrainerURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonEmailKey, is(userEmail)));
    }
}
