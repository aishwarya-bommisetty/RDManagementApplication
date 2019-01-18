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
 * Test Cases for checking registration based on last name property.
 *
 * @author Priyanka_Sarkar
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTestForLastName {
  @Value("${host.url}")
  private String hostUri;
  @Value("${profile.endpoint}")
  private String profileEndpoint;
  @Value("${invalid-name.message}")
  private String invalidNameMessage;
  @Value("${user-already-exists.message}")
  private String userAlreadyExistsMessage;
  @Value("${register-success.message}")
  private String registerSuccessMessage;
  @Value("${valid-email}")
  private String validEmail;
  @Value("${valid-firstname}")
  private String validFirstName;
  @Value("${valid-lastname}")
  private String validLastName;
  @Value("${valid-password}")
  private String validPassword;
  @Value("${invalid-lastname.minlength}")
  private String invalidLastNameWithMinLength;
  @Value("${invalid-lastname.maxlength}")
  private String invalidLastNameWithMaxLength;
  @Value("${invalid-lastname.digit}")
  private String invalidLastNameWithDigit;
  @Value("${invalid-lastname.specialcharacter}")
  private String invalidLastNameWithSpclChar;
  @Autowired
  private MockMvc mvc;
  @MockBean
  private RegistrationService registrationService;

  /**
   * JSON Request from the request body for registration.
   */
  private String registrationRequestJson;

  /**
   * Test when the last name contains a digit.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithLastNameHavingDigit() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(invalidLastNameWithDigit);
    user.setPassword(validPassword);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(invalidNameMessage)));
  }

  /**
   * Test when the value for last name is null.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithLastNameHavingNullValue() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(null);
    user.setPassword(validPassword);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(invalidNameMessage)));
  }

  /**
   * Test when the last name contains less than the minimum number of characters.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithLastNameHavingSizeLessThanMinimum() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(invalidLastNameWithMinLength);
    user.setPassword(validPassword);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(invalidNameMessage)));
  }

  /**
   * Test when the last name contains more than the maximum number of characters.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithLastNameHavingSizeMoreThanMaximum() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(invalidLastNameWithMaxLength);
    user.setPassword(validPassword);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(invalidNameMessage)));
  }

  /**
   * Test when the last name contains a special character.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithLastNameHavingSpecialCharacter() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(invalidLastNameWithSpclChar);
    user.setPassword(validPassword);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(invalidNameMessage)));
  }
}

