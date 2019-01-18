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

import com.epam.rdmanagement.exception.InvalidEmailException;
import com.epam.rdmanagement.exception.NoTrainerAssignedException;
import com.epam.rdmanagement.model.StudentModel;
import com.epam.rdmanagement.model.TrainerModel;
import com.epam.rdmanagement.service.StudentService;

/**
 * 
 * @author Anand_Edasseril
 *
 * Test cases for student com.epam.rdmanagement.controller.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class)
@PropertySource("classpath:application-test.properties")
public class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    /**
     * Mocking student service.
     */
    @MockBean
    private StudentService studentService;

    /**
     * URL path to get student by email..
     */
    @Value("${get-student-by-email.url}")
    private String getStudentByEmailURL;

    /**
     * URL path to get student by email..
     */
    @Value("${get-trainer.url}")
    private String getTrainerURL;

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
     * Email id of a student.
     */
    @Value("${student-email}")
    private String studentEmail;

    /**
     * json response key for email.
     */
    @Value("${json-user-email.key}")
    private String jsonEmailKey;
    
    /**
     * URL to get profile of student.
     */
    @Value("${get-student-profile.url}")
    private String getStudentProfileURL;
    
    /**
     * Testing the get student profile .
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "STUDENT")
    public void testGetStudentProfile() throws Exception {

        StudentModel user = new StudentModel();
        user.setEmail(studentEmail);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);

        when(studentService.getProfile(studentEmail)).thenReturn(user);
        mockMvc.perform(get(getStudentProfileURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonEmailKey, is(studentEmail)));
    }

    /**
     * Testing the getStudentByEmail com.epam.rdmanagement.controller.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "STUDENT")
    public void testGetStudentByEmail() throws Exception {

        StudentModel student = new StudentModel();
        student.setEmail(userEmail);
        student.setFirstName(userFirstName);
        student.setLastName(userLastName);

        when(studentService.getStudentByEmail(studentEmail)).thenReturn(student);
        mockMvc.perform(get(getStudentByEmailURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonEmailKey, is(userEmail)));
    }

    /**
     * Testing getStudentByEmail with invalid Email id.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "STUDENT")
    public void testGetStudentByEmailWithInvalidUserName() throws Exception {
        Mockito.doThrow(InvalidEmailException.class).when(studentService).getStudentByEmail(studentEmail);
        mockMvc.perform(get(getStudentByEmailURL)).andExpect(status().isBadRequest());
    }

    /**
     * Testing the getTrainer com.epam.rdmanagement.controller.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "STUDENT")
    public void testGetTrainer() throws Exception {

        TrainerModel trainer = new TrainerModel();
        trainer.setEmail(userEmail);
        trainer.setFirstName(userFirstName);
        trainer.setLastName(userLastName);

        when(studentService.getTrainer(studentEmail)).thenReturn(trainer);
        mockMvc.perform(get(getTrainerURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonEmailKey, is(userEmail)));
    }

    /**
     * Testing getTrainer with invalid username.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "STUDENT")
    public void testGetTrainerWithInvalidUserName() throws Exception {
        Mockito.doThrow(InvalidEmailException.class).when(studentService).getTrainer(studentEmail);
        mockMvc.perform(get(getTrainerURL)).andExpect(status().isBadRequest());
    }

    /**
     * Testing getTrainer with invalid username.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "STUDENT")
    public void testGetTrainerWithNoAssignedTrainer() throws Exception {
        Mockito.doThrow(NoTrainerAssignedException.class).when(studentService).getTrainer(studentEmail);
        mockMvc.perform(get(getTrainerURL)).andExpect(status().isBadRequest());
    }
}
