package com.epam.rdmanagement.controller.integerationtests;

import com.epam.rdmanagement.model.UserModel;
import com.epam.rdmanagement.util.ConstantsUtil;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@DatabaseSetup(ConstantsUtil.DBUNIT_DATASET_PATH)
public class RegistrationControllerIntegrationTestForEmail {

  @LocalServerPort
  private int port;
  @Value("${sign-up.endpoint}")
  private String registerEndpoint;
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

  @Before
  public void setUp() throws Exception {
    RestAssured.port = port;
  }

  /**
   * Test when all the details passed are correct.
   * 
   */
  @Test
  public void testRegistrationWithValidInputs() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(201, response.getStatusCode());
    Assert.assertEquals(registerSuccessMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when already existing user email id is provided.
   * 
   */
  @Test
  public void testRegistrationWithDuplicateEmailId() throws Exception {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(409, response.getStatusCode());
    Assert.assertEquals(userAlreadyExistsMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when email id value is null.
   * 
   */
  @Test
  public void testRegistrationWithEmailIdHavingNullValue() {

    UserModel user = new UserModel();
    user.setEmail(null);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidEmailMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when email id has invalid domain name.
   * 
   */
  @Test
  public void testRegitrationWithEmailIdHavingInvalidDomain() {

    UserModel user = new UserModel();
    user.setEmail(invalidEmailDomain);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidEmailMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when user email id is does not have the proper format.
   * 
   */
  @Test
  public void testRegistrationWithInvalidEmailId() throws Exception {

    UserModel user = new UserModel();
    user.setEmail(invalidEmailFormat);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidEmailMessage, response.jsonPath().get("message"));
  }

  /**
   * Returns a response of give user registration request.
   * 
   */
  private Response getResponse(UserModel user) {
    RequestSpecification request = RestAssured.given();
    request.header("Content-Type", "application/json");
    request.body(user);
    Response response = request.post(registerEndpoint);
    return response;
  }

}
