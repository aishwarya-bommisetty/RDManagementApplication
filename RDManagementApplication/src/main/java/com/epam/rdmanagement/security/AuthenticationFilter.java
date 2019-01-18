package com.epam.rdmanagement.security;

import com.epam.rdmanagement.util.ConstantsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;

  public AuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
    try {
      com.epam.rdmanagement.model.UserModel creds = new ObjectMapper()
          .readValue(req.getInputStream(), com.epam.rdmanagement.model.UserModel.class);

      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          creds.getEmail(), creds.getPassword(), new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain, Authentication auth) throws IOException, ServletException {
    final String authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));
    String token = Jwts.builder().setSubject(((User) auth.getPrincipal()).getUsername())
        .claim(ConstantsUtil.AUTHORITIES_KEY, authorities)
        .setExpiration(new Date(System.currentTimeMillis() + ConstantsUtil.EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, ConstantsUtil.SECRET_KEY).compact();

    res.addHeader(ConstantsUtil.HEADER_STRING, ConstantsUtil.TOKEN_PREFIX + token);
  }
}

