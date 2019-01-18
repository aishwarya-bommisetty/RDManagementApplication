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
public class RegistrationControllerIntegrationTestForFirstName {

  @LocalServerPort
  private int port;
  @Value("${sign-up.endpoint}")
  private String registerEndpoint;
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

  @Before
  public void setUp() throws Exception {
    RestAssured.port = port;
  }

  /**
   * Test when the value for first name is null.
   * 
   */
  @Test
  public void testRegistrationWithFirstNameHavingNullValue() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(null);
    user.setLastName(validLastName);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidNameMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when the first name contains less than the minimum number of characters.
   * 
   */
  @Test
  public void testRegistrationWithFirstNameHavingSizeLessThanMinimum() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(invalidFirstNameWithMinLength);
    user.setLastName(validLastName);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidNameMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when the first name contains more than the maximum number of characters.
   * 
   */
  @Test
  public void testRegistrationWithFirstNameHavingSizeMoreThanMaximum() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(invalidFirstNameWithMaxLength);
    user.setLastName(validLastName);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidNameMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when the first name contains a digit.
   * 
   */
  @Test
  public void testRegistrationWithFirstNameHavingDigit() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(invalidFirstNameWithDigit);
    user.setLastName(validLastName);
    user.setPassword(validPassword);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidNameMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when the first name contains a special character.
   * 
   */
  @Test
  public void testRegistrationWithFirstNameHavingSpecialCharacter() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(invalidFirstNameWithSpclChar);
    user.setLastName(validLastName);
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
