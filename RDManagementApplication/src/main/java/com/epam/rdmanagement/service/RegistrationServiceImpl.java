package com.epam.rdmanagement.service;

import com.epam.rdmanagement.controller.RegistrationController;
import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.entity.StudentEntity;
import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.exception.UserAlreadyExistsException;
import com.epam.rdmanagement.model.UserModel;
import com.epam.rdmanagement.repository.StudentRepository;
import com.epam.rdmanagement.repository.UserRepository;

import org.dozer.DozerBeanMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private StudentRepository studentRepository;
  @Value("${student.role-id}")
  private String studentRoleId;
  @Value("${user-already-exists-exception.message}")
  private String userAlreadyExistsExceptionMessage;

  private void checkIfUserAlreadyExists(String email) throws UserAlreadyExistsException {
    if (userRepository.findByEmail(email) != null) {
      throw new UserAlreadyExistsException(userAlreadyExistsExceptionMessage);
    }
  }

  /**
   * Service to register the student with the user entity passed from the
   * {@link RegistrationController}.
   * It validates the user data based on different business requirements.
   *
   * @throws UserAlreadyExistsException
   *
   */
  @Override
  public void registerStudent(UserModel userModel) throws UserAlreadyExistsException {
    checkIfUserAlreadyExists(userModel.getEmail().toLowerCase());

    DozerBeanMapper mapper = new DozerBeanMapper();
    StudentEntity userEntity = mapper.map(userModel, StudentEntity.class);

    userEntity.setEmail(userModel.getEmail().toLowerCase());

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    userEntity.setRole(new RoleEntity(Integer.parseInt(studentRoleId)));
    userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
    studentRepository.save(userEntity);
  }
}

