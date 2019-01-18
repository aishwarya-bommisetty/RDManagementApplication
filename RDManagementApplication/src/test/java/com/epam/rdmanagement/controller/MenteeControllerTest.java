package com.epam.rdmanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.epam.rdmanagement.exception.MenteeNotFoundException;
import com.epam.rdmanagement.exception.MentorNotAvailableException;
import com.epam.rdmanagement.model.MenteeModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.service.MenteeService;

/**
 * 
 * @author Anand_Edasseril
 * 
 * Test cases for Trainer com.epam.rdmanagement.controller.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = MenteeController.class)
@PropertySource("classpath:application-test.properties")
public class MenteeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocking trainer service.
     */
    @MockBean
    private MenteeService menteeService;

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
     * URL path to get mentor of a mentee.
     */
    @Value("${get-mentor.url}")
    private String getMentorURL;

    /**
     * URL path to get mentee by email.
     */
    @Value("${get-mentee-by-email}")
    private String getMenteeByEmailURL;

    /**
     * Name of the trainer.
     */
    @Value("${mentee-email}")
    private String menteeEmail;

    /**
     * json response key for email.
     */
    @Value("${json-user-email.key}")
    private String jsonEmailKey;
    
    
    /**
     * URL to get profile of mentee.
     */
    @Value("${get-mentee-profile.url}")
    private String getMenteeProfileURL;
      
    /**
     * Testing the get mentee profile .
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "MENTEE")
    public void testGetMenteeProfile() throws Exception {

        MenteeModel user = new MenteeModel();
        user.setEmail(menteeEmail);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);

        when(menteeService.getProfile(menteeEmail)).thenReturn(user);
        mockMvc.perform(get(getMenteeProfileURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonEmailKey, is(menteeEmail)));
    }

    /**
     * Testing the get mentor of a mentee
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "MENTEE")
    public void testGetMentor() throws Exception {

        UserProfileModel student = new UserProfileModel();
        student.setEmail(userEmail);
        student.setFirstName(userFirstName);
        student.setLastName(userLastName);

        when(menteeService.getMentor(menteeEmail)).thenReturn(student);
        mockMvc.perform(get(getMentorURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath(jsonEmailKey, is(userEmail)));
    }

    /**
     * Testing throwing exception when mentor is not found.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "MENTEE")
    public void testGetMentorWhereNoMentorExists() throws Exception {

        Mockito.doThrow(MentorNotAvailableException.class).when(menteeService).getMentor(menteeEmail);
        mockMvc.perform(get(getMentorURL)).andExpect(status().isBadRequest());
    }

    /**
     * Testing the get mentee by email.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "MENTEE")
    public void testGetMenteeByEmail() throws Exception {

        MenteeModel user = new MenteeModel();
        user.setEmail(userEmail);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);

        when(menteeService.getMenteeByEmail(menteeEmail)).thenReturn(user);
        mockMvc.perform(get(getMenteeByEmailURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonEmailKey, is(userEmail)));
    }

    /**
     * Testing throwing exception when mentee is not found.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "MENTEE")
    public void testGetMenteeByEmailWhereNoMenteeExists() throws Exception {

        Mockito.doThrow(MenteeNotFoundException.class).when(menteeService).getMenteeByEmail(menteeEmail);
        mockMvc.perform(get(getMenteeByEmailURL)).andExpect(status().isBadRequest());
    }
}
