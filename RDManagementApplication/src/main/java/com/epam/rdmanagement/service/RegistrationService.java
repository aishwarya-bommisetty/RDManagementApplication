package com.epam.rdmanagement.service;

import com.epam.rdmanagement.exception.UserAlreadyExistsException;
import com.epam.rdmanagement.model.UserModel;

public interface RegistrationService {
  public void registerStudent(UserModel user) throws UserAlreadyExistsException;
}

