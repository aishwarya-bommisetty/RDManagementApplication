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
public class RegistrationControllerIntegrationTestForPassword {

  @LocalServerPort
  private int port;
  @Value("${sign-up.endpoint}")
  private String registerEndpoint;
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

  @Before
  public void setUp() throws Exception {
    RestAssured.port = port;
  }

  /**
   * Test when the value for password is null.
   * 
   */
  @Test
  public void testRegistrationWithPasswordHavingNullValue() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(null);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidPasswordMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when the password contains less than the minimum number of characters.
   * 
   */
  @Test
  public void testRegistrationWithPasswordHavingSizeLessThanMinimum() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(invalidPasswordWithMinLength);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidPasswordMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when the password contains more than the maximum number of characters.
   * 
   */
  @Test
  public void testRegistrationWithPasswordHavingSizeMoreThanMaximum() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(invalidPasswordWithMaxLength);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidPasswordMessage, response.jsonPath().get("message"));
  }

  /**
   * Test when the password provided does not follow the rules and constraints for
   * valid password.
   * 
   */
  @Test
  public void testRegistrationWithInvalidPassword() {

    UserModel user = new UserModel();
    user.setEmail(validEmail);
    user.setFirstName(validFirstName);
    user.setLastName(validLastName);
    user.setPassword(invalidPasswordFormat);

    Response response = getResponse(user);

    Assert.assertEquals(400, response.getStatusCode());
    Assert.assertEquals(invalidPasswordMessage, response.jsonPath().get("message"));
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
