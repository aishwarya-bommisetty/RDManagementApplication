package com.epam.rdmanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.epam.rdmanagement.model.FeedbackAddModel;
import com.epam.rdmanagement.model.RoleModel;
import com.epam.rdmanagement.service.AdminService;
import com.epam.rdmanagement.service.MenteeService;
import com.epam.rdmanagement.service.MentorService;
import com.epam.rdmanagement.service.ProfileService;
import com.epam.rdmanagement.service.StudentService;
import com.epam.rdmanagement.service.SuperAdminService;
import com.epam.rdmanagement.service.TrainerService;

/**
 * 
 * @author Anand_Edasseril
 *
 * Test cases to test profile com.epam.rdmanagement.controller.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = ProfileController.class)
@PropertySource("classpath:application-test.properties")
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocking Profile service.
     */
    @MockBean
    ProfileService profileService;
    
    /**
     * Mocking student service.
     */
    @MockBean
    StudentService studentService;
    
    /**
     * Mocking trianer service.
     */
    @MockBean
    TrainerService trainerService;
    
    /**
     * Mocking mentor service.
     */
    @MockBean
    MentorService mentorService;
    
    /**
     * Mocking mentee service
     */
    @MockBean
    MenteeService menteeService;
    
    /**
     * Mocking admin service.
     */
    @MockBean
    AdminService adminService;
    
    /**
     * Mocking super admin service.
     */
    @MockBean
    SuperAdminService superAdminService;

    /**
     * URL to get the role of a user.
     */
    @Value("${get-role.url}")
    private String getRoleURL;
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
     * Email of the admin.
     */
    @Value("${admin-email}")
    private String adminEmail;
   
    /**
     * json response key for email.
     */
    @Value("${json-user-email.key}")
    private String jsonEmailKey;

    /**
     * json response key for role name.
     */
    @Value("${json-role-name.key}")
    private String jsonRoleNameKey;
    
    /**
     * Student feedback.
     */
    @Value("${trainee-feedback}")
    private String userFeedback;
    
    /**
     * email id of the trainer.
     */
    @Value("${trainer-email}")
    private String trainerEmail;
    
    /**
     * email id of the trainer.
     */
    @Value("${student-email}")
    private String studentEmail;
    
    /**
     * URL to add feedback.
     */
    @Value("${add-feedback.url}")
    private String addFeedbackURL;
    
    /**
     * Feedback json.
     */
    @Value("${feedback-json}")
    private String feedbackJson;
    
    /**
     * json response key for feedback.
     */
    @Value("${json-feedback.key}")
    private String jsonFeedbackKey;
    
    /**
     * json response key for status.
     */
    @Value("${json-status.key}")
    private String jsonStatusKey;
    
    /**
     * Json response key for message.
     */
    @Value("${json-message.key}")
    private String jsonMessageKey;
    
    /**
     * Feedback added success message.
     */
    @Value("${feedback-added.message}")
    private String feedbackAddedMessage;

    /**
     * Testing the get role com.epam.rdmanagement.controller.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "ADMIN")
    public void testGetRole() throws Exception {

        RoleModel role = new RoleModel();
        role.setRoleId(3);
        role.setRoleName(studentRoleName);
        when(profileService.getRole(adminEmail)).thenReturn(role);
        mockMvc.perform(get(getRoleURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonRoleNameKey, is(studentRoleName)));
    }
    
    /**
     * Testing the addFeedback.
     */
    @Test
    @WithMockUser(username = "hari@epam.com", password = "Tedox@123", roles = "TRAINER")
    public void testAddFeedback() throws Exception {

        FeedbackAddModel feedback = new FeedbackAddModel();
        feedback.setFeedback(userFeedback);
        feedback.setFromUserEmail(trainerEmail);
        feedback.setToUserEmail(studentEmail);

        when(profileService.giveFeedback(feedback)).thenReturn(true);
        mockMvc.perform(post(addFeedbackURL).contentType(MediaType.APPLICATION_JSON).content(feedbackJson)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath(jsonStatusKey, Matchers.equalTo(201)))
                .andExpect(jsonPath(jsonMessageKey, Matchers.equalTo(feedbackAddedMessage)));
    }

}
