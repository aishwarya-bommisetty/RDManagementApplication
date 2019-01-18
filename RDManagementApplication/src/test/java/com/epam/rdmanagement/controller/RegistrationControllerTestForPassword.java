package com.epam.rdmanagement.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.epam.rdmanagement.model.UserModel;
import com.epam.rdmanagement.service.RegistrationService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test Cases for checking registration based on password property.
 *
 * @author Priyanka_Sarkar
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTestForPassword {
  @Value("${host.url}")
  private String hostUri;
  @Value("${profile.endpoint}")
  private String profileEndpoint;
  @Value("${invalid-password.message}")
  private String invalidPasswordMessage;
  @Value("${valid-email}")
  private String validEmail;
  @Value("${valid-firstname}")
  private String validFirstName;
  @Value("${valid-lastname}")
  private String validLastName;
  @Value("${valid-password}")
  private String validPassword;
  @Value("${invalid-password.minlength}")
  private String invalidPasswordWithMinLength;
  @Value("${invalid-password.maxlength}")
  private String invalidPasswordWithMaxLength;
  @Value("${invalid-password.format}")
  private String invalidPasswordFormat;
  @Autowired
  private MockMvc mvc;
  @MockBean
  private RegistrationService registrationService;

  /**
   * JSON Request from the request body for registration.
   */
  private String registrationRequestJson;

  /**
   * Test when the password provided does not follow the rules and constraints for
   * valid password.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithInvalidPassword() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(invalidPasswordFormat);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(invalidPasswordMessage)));
  }

  /**
   * Test when the value for password is null.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithPasswordHavingNullValue() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(null);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(invalidPasswordMessage)));
  }

  /**
   * Test when the password contains less than the minimum number of characters.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithPasswordHavingSizeLessThanMinimum() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(invalidPasswordWithMinLength);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(invalidPasswordMessage)));
  }

  /**
   * Test when the password contains more than the maximum number of characters.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithPasswordHavingSizeMoreThanMaximum() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(invalidPasswordWithMaxLength);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(invalidPasswordMessage)));
  }
}

