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
import com.epam.rdmanagement.service.SuperAdminService;

/**
 * 
 * @author Anand_Edasseril
 *
 * Test cases to test Super admin com.epam.rdmanagement.controller.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = SuperAdminController.class)
@PropertySource("classpath:application-test.properties")
public class SuperAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocking Super Admin service.
     */
    @MockBean
    private SuperAdminService superAdminService;

    /**
     * URL path to add admin..
     */
    @Value("${add-admin.url}")
    private String addAdminURL;

    /**
     * URL path to get all admins.
     */
    @Value("${get-all-admin.url}")
    private String getAllAdminURL;

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
     * Role name for admin.
     */
    @Value("${admin.role-name}")
    private String adminRoleName;

    /**
     * User data in json format.
     */
    @Value("${user-json}")
    private String jsonUserData;

    /**
     * Name of the admin.
     */
    @Value("${super-admin-name}")
    private String superAdminName;

    /**
     * Success message after added admin.
     */
    @Value("${added-admin-success.message}")
    private String addedAdminSuccessMessage;

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
    private String jsonSuperAdminEmailKey;
    
    /**
     * Email of the admin.
     */
    @Value("${super-admin-email}")
    private String superAdminEmail;
       
    /**
     * URL to get profile of super admin.
     */
    @Value("${get-super-admin-profile.url}")
    private String getSuperAdminProfileURL;
    
    /**
     * Testing the get super admin profile .
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "SUPERADMIN")
    public void testGetSuperAdminProfile() throws Exception {

        UserProfileModel user = new UserProfileModel();
        user.setEmail(superAdminEmail);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);

        when(superAdminService.getProfile(superAdminEmail)).thenReturn(user);
        mockMvc.perform(get(getSuperAdminProfileURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonSuperAdminEmailKey, is(superAdminEmail)));
    }

    /**
     * Testing the addAdmin com.epam.rdmanagement.controller.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "SUPERADMIN")
    public void testAddAdmin() throws Exception {

        UserAddModel user = new UserAddModel();
        user.setEmail(userEmail);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);
        user.setPassword(userPassword);

        String adminJson = new String(jsonUserData);
        when(superAdminService.addAdmin(user)).thenReturn(addedAdminSuccessMessage);
        mockMvc.perform(post(addAdminURL).contentType(MediaType.APPLICATION_JSON).content(adminJson)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath(jsonStatusKey, Matchers.equalTo(201)))
                .andExpect(jsonPath(jsonMessageKey, Matchers.equalTo(addedAdminSuccessMessage)));

    }

    /**
     * Testing the getAllAdmins com.epam.rdmanagement.controller.
     */
    @Test
    @WithMockUser(username = "john_doe@epam.com", password = "Tedox@123", roles = "SUPERADMIN")
    public void testGetAllAdmins() throws Exception {

        UserProfileModel user = new UserProfileModel();
        user.setEmail(userEmail);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);

        List<UserProfileModel> users = Arrays.asList(user);
        when(superAdminService.getAllAdmins()).thenReturn(users);
        mockMvc.perform(get(getAllAdminURL)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath(jsonEmailKey, is(userEmail)));
    }

}
