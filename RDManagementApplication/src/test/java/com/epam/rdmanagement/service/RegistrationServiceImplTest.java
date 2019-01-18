package com.epam.rdmanagement.service;

import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.exception.UserAlreadyExistsException;
import com.epam.rdmanagement.model.UserModel;
import com.epam.rdmanagement.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistrationServiceImpl.class)
public class RegistrationServiceImplTest {
  @Value("${test-email.value2}")
  private String testEmailValue2;
  @Value("${test-firstname.value}")
  private String testFirstNameValue;
  @Value("${test-lastname.value}")
  private String testLastNameValue;
  @Value("${test-password.value}")
  private String testPasswordValue;

  /**
   * registrationService dependency.
   */
  @Autowired
  private RegistrationService registrationService;

  /**
   * Mocking userRepository.
   */
  @MockBean
  private UserRepository userRepository;

  /**
   * User registration test.
   *
   * @throws InvalidPasswordException     if the password is invalid
   * @throws InvalidEmailException        if the email is invalid
   * @throws UserAlreadyExistsException   if the user already exists
   * @throws InsufficientDetailsException if the user skips any details
   */
  @Test
  public void testPositiveRegistrationCase() throws UserAlreadyExistsException {
    UserModel userModel = new UserModel();

    userModel.setFirstName(testFirstNameValue);
    userModel.setLastName(testLastNameValue);
    userModel.setPassword(testPasswordValue);
    userModel.setEmail(testEmailValue2);
    Mockito.when(userRepository.findByEmail(testEmailValue2)).thenReturn(null);
    registrationService.registerStudent(userModel);
  }

  /**
   * User registration test.
   *
   * @throws InvalidPasswordException     if the password is invalid
   * @throws InvalidEmailException        if the email is invalid
   * @throws UserAlreadyExistsException   if the user already exists
   * @throws InsufficientDetailsException if the user skips any details
   */
  @Test(expected = UserAlreadyExistsException.class)
  public void testRegistrationCaseWithExistingUser() throws UserAlreadyExistsException {
    UserModel userModel = new UserModel();

    userModel.setFirstName(testFirstNameValue);
    userModel.setLastName(testLastNameValue);
    userModel.setPassword(testPasswordValue);
    userModel.setEmail(testEmailValue2);
    Mockito.when(userRepository.findByEmail(testEmailValue2)).thenReturn(new UserEntity());
    registrationService.registerStudent(userModel);
  }

  /**
   * Setup before each test is called.
   *
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    UserEntity userEntity = new UserEntity();

    userEntity.setEmail(testEmailValue2);
    userEntity.setFirstName(testFirstNameValue);
    userEntity.setLastName(testLastNameValue);
    userEntity.setPassword(testPasswordValue);
    Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);
  }

  /**
   * Static inner class to inject RegistrationService dependency.
   *
   * @author Sandeep_Addala
   *
   */
  @TestConfiguration
  static class EmployeeServiceImplTestContextConfiguration {
    @Bean
    public RegistrationService registrationService() {
      return new RegistrationServiceImpl();
    }
  }
}

