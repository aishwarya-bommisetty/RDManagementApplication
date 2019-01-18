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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dbtest")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@DatabaseSetup(ConstantsUtil.DBUNIT_DATASET_PATH)
public class RegistrationControllerIntegrationTestForLastName {

  @LocalServerPort
  private int port;
  @Value("${sign-up.endpoint}")
  private String registerEndpoint;
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

  @Before
  public void setUp() throws Exception {
    RestAssured.port = port;
  }

  /**
   * Test when the value for last name is null.
   * 
   */
  @Test
  public void testRegistrationWithLastNameHavingNullValue() throws Exception {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(null);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidNameMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when the last name contains less than the minimum number of characters.
   * 
   */
  @Test
  public void testRegistrationWithLastNameHavingSizeLessThanMinimum() throws Exception {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(invalidLastNameWithMinLength);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidNameMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when the last name contains more than the maximum number of characters.
   * 
   */
  @Test
  public void testRegistrationWithLastNameHavingSizeMoreThanMaximum() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(invalidLastNameWithMaxLength);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidNameMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when the last name contains a digit.
   * 
   */
  @Test
  public void testRegistrationWithLastNameHavingDigit() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(invalidLastNameWithDigit);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidNameMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when the last name contains a special character.
   * 
   */
  @Test
  public void testRegistrationWithLastNameHavingSpecialCharacter() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(invalidLastNameWithSpclChar);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidNameMessage, response.jsonPath().get("message"));
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
