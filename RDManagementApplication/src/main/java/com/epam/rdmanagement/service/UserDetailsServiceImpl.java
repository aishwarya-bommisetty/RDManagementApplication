package com.epam.rdmanagement.service;

import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;
  @Value("${role-name.suffix}")
  private String roleNameSuffix;

  /**
   * Method implementation of {@link UserDetailsService} of loadByUsername. based
   * upon the email which is user name in this case.
   *
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByEmail(email.toLowerCase());
    if (user == null) {
      throw new UsernameNotFoundException(email.toLowerCase());
    }

    Set<GrantedAuthority> authority = new HashSet<>();

    authority.add(new SimpleGrantedAuthority(roleNameSuffix + user.getRole().getRoleName()));

    return new User(user.getEmail(), user.getPassword(), authority);
  }
}

