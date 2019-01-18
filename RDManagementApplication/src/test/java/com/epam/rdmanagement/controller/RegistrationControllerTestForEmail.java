package com.epam.rdmanagement.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.epam.rdmanagement.exception.UserAlreadyExistsException;
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
 * Test Cases for checking registration based on email property.
 *
 * @author Priyanka_Sarkar
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTestForEmail {
  @Value("${host.url}")
  private String hostUri;
  @Value("${profile.endpoint}")
  private String profileEndpoint;
  @Value("${invalid-email.message}")
  private String invalidEmailMessage;
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
  @Value("${invalid-email.domain}")
  private String invalidEmailDomain;
  @Value("${invalid-email.format}")
  private String invalidEmailFormat;
  @Autowired
  private MockMvc mvc;
  @MockBean
  private RegistrationService registrationService;

  /**
   * JSON Request from the request body for registration.
   */
  private String registrationRequestJson;

  /**
   * Test when already existing user email id is provided.
   *
   * @throws Exception
   */
  @Test
  public void testRegistrationWithDuplicateEmailId() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(validPassword);
    Mockito.doThrow(UserAlreadyExistsException.class).when(registrationService)
        .registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.CONFLICT.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(userAlreadyExistsMessage)));
  }

  /**
   * Test when email id value is null.
   *
   * @throws Exception
   */
  @Test
  public void testRegistrationWithEmailIdHavingNullValue() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(null);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(validPassword);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(invalidEmailMessage)));
  }

  /**
   * Test when user email id is does not have the proper format.
   *
   * @throws Exception
   */
  @Test
  public void testRegistrationWithInvalidEmailId() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(invalidEmailFormat);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(validPassword);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(invalidEmailMessage)));
  }

  /**
   * Test when all the details passed are correct.
   *
   * @throws Exception
   */
  @Test
  public void testRegistrationWithValidInputs() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(validPassword);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);

    StringBuilder expectedResourceLocation = new StringBuilder(hostUri);

    expectedResourceLocation.append(profileEndpoint)
        .append(user.getEmail().substring(0, user.getEmail().indexOf('@')).toLowerCase());
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.CREATED.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(registerSuccessMessage))).andExpect(
            jsonPath("$.resource-location", Matchers.equalTo(expectedResourceLocation.toString())));
  }

  /**
   * Test when email id has invalid domain name.
   *
   * @throws Exception
   */
  @Test
  public void testRegitrationWithEmailIdHavingInvalidDomain() throws Exception {
    UserModel user = new UserModel();

    user.setEmail(invalidEmailDomain);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(validPassword);
    Mockito.doNothing().when(registrationService).registerStudent(user);

    ObjectMapper mapper = new ObjectMapper();

    registrationRequestJson = mapper.writeValueAsString(user);
    mvc.perform(post("/register").accept(MediaType.APPLICATION_JSON)
        .content(registrationRequestJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", Matchers.equalTo(HttpStatus.BAD_REQUEST.value())))
        .andExpect(jsonPath("$.message", Matchers.equalTo(invalidEmailMessage)));
  }
}

