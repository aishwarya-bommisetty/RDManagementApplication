package com.epam.rdmanagement.security;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Value("${sign-up.endpoint}")
  private String signUpUrl;
  @Value("${resources.path}")
  private String resourcesPath;
  @Value("${static.path}")
  private String staticPath;
  @Value("${css.path}")
  private String cssPath;
  @Value("${js.path}")
  private String jsPath;
  @Value("${images.path}")
  private String imagesPath;
  @Value("${invalid-credentials.message}")
  private String invalidCredentialsMessage;
  @Value("${title.status}")
  private String statusTitle;
  @Value("${title.message}")
  private String messageTitle;

  private UserDetailsService userDetailsService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public SecurityConfiguration(UserDetailsService userDetailsService,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userDetailsService = userDetailsService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  /**
   * Entry point bean to handle request and response content for authentication
   * with custom message.
   * 
   * @return
   */
  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return (request, response, authException) -> {
      response.setHeader("Content-Type", "application/json");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      Map<String,String> responseJsonMap = new HashMap<>();
      responseJsonMap.put(statusTitle, HttpStatus.UNAUTHORIZED.toString());
      responseJsonMap.put(messageTitle, invalidCredentialsMessage);
      response.getWriter().write(new Gson().toJson(responseJsonMap));
    };
  }

  /**
   * Security Configuration to authenticate the user details by client.
   */
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  /**
   * Security Configuration for the http requests from the client.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, signUpUrl).permitAll()
        .anyRequest().authenticated().and()
        .addFilter(new AuthenticationFilter(authenticationManager()))
        .addFilter(new AuthorizationFilter(authenticationManager())).sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.httpBasic().authenticationEntryPoint(authenticationEntryPoint());
    http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
  }

  /**
   * Security configuration to ignore any URI that directly tries to access
   * resources.
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(resourcesPath, staticPath, cssPath, jsPath, imagesPath);
  }
}
