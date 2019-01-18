package com.epam.rdmanagement.controller.integerationtests;

import com.epam.rdmanagement.util.ConstantsUtil;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
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
public class AuthenticationTest {
  
  @LocalServerPort
  private int port;
  @Value("${login.endpoint}")
  private String loginEndpoint;
  @Value("${test-email.value2}")
  private String testEmail;
  @Value("${test-email.value}")
  private String testEmail2;
  @Value("${test-password}")
  private String testPassword;
  @Value("${title.email}")
  private String emailTitle;
  @Value("${title.password}")
  private String passwordTitle;
  @Value("${title.message}")
  private String messageTitle;
  @Value("${invalid-credentials.message}")
  private String invalidCredentialsMessage;
  
  @Before
  public void setUp() throws Exception {
    RestAssured.port = port;
  }

  /**
   * Test when all the details passed are correct.
   * 
   */
  @Test
  public void testLoginWithValidCredentials() {

    Map<String,String> loginRequestJson = new HashMap<>();
    
    loginRequestJson.put(emailTitle, testEmail);
    loginRequestJson.put(passwordTitle, testPassword);

    Response response = getResponse(new Gson().toJson(loginRequestJson));
    Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
  }
  
  /**
   * Test when the details passed are incorrect.
   * 
   */
  @Test
  public void testLoginWithInvalidCredentials() {

    Map<String,String> loginRequestJson = new HashMap<>();
    
    loginRequestJson.put(emailTitle, testEmail2);
    loginRequestJson.put(passwordTitle, testPassword);

    Response response = getResponse(new Gson().toJson(loginRequestJson));
    Assert.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
    Assert.assertEquals(invalidCredentialsMessage, response.jsonPath().get(messageTitle));
    
  }
  
  /**
   * Returns a response of give login request.
   * 
   */
  private Response getResponse(String loginRequestJson) {
    RequestSpecification request = RestAssured.given();
    request.header("Content-Type", "application/json");
    request.body(loginRequestJson);
    Response response = request.post(loginEndpoint);
    return response;
  }
}
