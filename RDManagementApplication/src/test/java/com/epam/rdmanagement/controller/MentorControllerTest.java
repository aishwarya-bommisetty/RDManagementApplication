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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.rdmanagement.exception.MenteesNotFoundException;
import com.epam.rdmanagement.model.MenteeModel;
import com.epam.rdmanagement.model.MentorModel;
import com.epam.rdmanagement.service.MentorService;

/**
 * 
 * @author Anand_Edasseril
 *
 * Test cases to test Mentor com.epam.rdmanagement.controller.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = MentorController.class)
@PropertySource("classpath:application-test.properties")
public class MentorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocking mentor service.
     */
    @MockBean
    private MentorService mentorService;

    /**
     * URL to get profile of mentor.
     */
    @Value("${get-mentor-profile.url}")
    private String getMentorProfileURL;
    
    /**
     * URL to Show mentees of a mentor.
     */
    @Value("${show-mentees.url}")
    private String showMenteesURL;

    /**
     * Name of the admin.
     */
    @Value("${mentor-email}")
    private String mentorEmail;

    /**
     * json response key for email.
     */
    @Value("${json-email.key}")
    private String jsonEmailKey;

    /**
     * json response key for email.
     */
    @Value("${json-user-email.key}")
    private String jsonEmailMentorKey;
    
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
     * Testing the get mentor profile .
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "MENTOR")
    public void testGetMentorProfile() throws Exception {

        MentorModel user = new MentorModel();
        user.setEmail(mentorEmail);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);

        when(mentorService.getProfile(mentorEmail)).thenReturn(user);
        mockMvc.perform(get(getMentorProfileURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonEmailMentorKey, is(mentorEmail)));
    }

    /**
     * Testing the show mentees com.epam.rdmanagement.controller.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "MENTOR")
    public void testShowMentees() throws Exception {

        MenteeModel mentee = new MenteeModel();
        mentee.setEmail(userEmail);
        mentee.setFirstName(userFirstName);
        mentee.setLastName(userLastName);

        List<MenteeModel> mentees = Arrays.asList(mentee);
        when(mentorService.getAllMenteesUnderMentor(mentorEmail)).thenReturn(mentees);
        mockMvc.perform(get(showMenteesURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonEmailKey, is(userEmail)));
    }

    /**
     * Testing throwing exception when no mentees are found for a mentor.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "MENTOR")
    public void testShowMenteesWithNoMenteesForMentor() throws Exception {

        Mockito.doThrow(MenteesNotFoundException.class).when(mentorService).getAllMenteesUnderMentor(mentorEmail);
        mockMvc.perform(get(showMenteesURL)).andExpect(status().isMethodNotAllowed());
    }

}
