package com.epam.rdmanagement.service;

import static org.junit.Assert.assertEquals;

import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserDetailsServiceImpl.class)
public class UserDetailsServiceImplTest {

  /**
   * Mocking userRepository.
   */
  @MockBean
  private UserRepository userRepository;
  @Value("${test-email.value2}")
  private String testEmailValue2;
  @Value("${test-firstname.value}")
  private String testFirstNameValue;
  @Value("${test-lastname.value}")
  private String testLastNameValue;
  @Value("${test-password.value}")
  private String testPasswordValue;
  @Value("${student.role-id}")
  private int studentRoleId;
  @Autowired
  private UserDetailsService userDetailsService;

  /**
   * Test to check the implementation of loadByUsername method with invalid
   * username i.e mail id in our case.
   */
  @Test(expected = UsernameNotFoundException.class)
  public void testLoadUserByInvalidUsername() {
    String invalidEmail = testEmailValue2;

    Mockito.when(userRepository.findByEmail(invalidEmail)).thenReturn(null);
    userDetailsService.loadUserByUsername(invalidEmail);
  }

  /**
   * Test to check the implementation of loadByUsername method with valid username
   * i.e mail id in our case.
   */
  @Test
  public void testLoadUserByValidUsername() {
    String expectedEmail = testEmailValue2;
    UserEntity user = new UserEntity();

    user.setEmail(expectedEmail);
    user.setPassword(testPasswordValue);
    user.setFirstName(testFirstNameValue);
    user.setLastName(testLastNameValue);
    user.setRole(new RoleEntity(studentRoleId));
    Mockito.when(userRepository.findByEmail(expectedEmail)).thenReturn(user);

    UserDetails actualUser = userDetailsService.loadUserByUsername(expectedEmail);

    assertEquals(actualUser.getUsername(), expectedEmail);
  }

  /**
   * Static inner class to inject {@link UserDetailsService} dependency.
   *
   * @author Sarthak_Jain
   *
   */
  @TestConfiguration
  static class UserDetailsServiceImplTestContextConfiguration {
    @Bean
    public UserDetailsService registrationService() {
      return new UserDetailsServiceImpl();
    }
  }
}

