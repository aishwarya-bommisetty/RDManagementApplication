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
 * Test Cases for checking registration based on first name property.
 *
 * @author Priyanka_Sarkar
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTestForFirstName {
  @Value("${host.url}")
  private String hostUri;
  @Value("${profile.endpoint}")
  private String profileEndpoint;
  @Value("${invalid-name.message}")
  private String invalidNameMessage;
  @Value("${valid-email}")
  private String validEmail;
  @Value("${valid-firstname}")
  private String validFirstName;
  @Value("${valid-lastname}")
  private String validLastName;
  @Value("${valid-password}")
  private String validPassword;
  @Value("${invalid-firstname.minlength}")
  private String invalidFirstNameWithMinLength;
  @Value("${invalid-firstname.maxlength}")
  private String invalidFirstNameWithMaxLength;
  @Value("${invalid-firstname.digit}")
  private String invalidFirstNameWithDigit;
  @Value("${invalid-firstname.specialcharacter}")
  private String invalidFirstNameWithSpclChar;
  @Autowired
  private MockMvc mvc;
  @MockBean
  private RegistrationService registrationService;

  /**
   * JSON Request from the request body for registration.
   */
  private String registrationRequestJson;

  /**
   * Test when the first name contains a digit.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithFirstNameHavingDigit() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(invalidFirstNameWithDigit);
    user.setLastName(validLastName);
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
   * Test when the value for first name is null.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithFirstNameHavingNullValue() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(null);
    user.setLastName(validLastName);
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
   * Test when the first name contains less than the minimum number of characters.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithFirstNameHavingSizeLessThanMinimum() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(invalidFirstNameWithMinLength);
    user.setLastName(validLastName);
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
   * Test when the first name contains more than the maximum number of characters.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithFirstNameHavingSizeMoreThanMaximum() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(invalidFirstNameWithMaxLength);
    user.setLastName(validLastName);
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
   * Test when the first name contains a special character.
   *
   * @throws Exception
   *
   */
  @Test
  public void testRegistrationWithFirstNameHavingSpecialCharacter() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(invalidFirstNameWithSpclChar);
    user.setLastName(validLastName);
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

