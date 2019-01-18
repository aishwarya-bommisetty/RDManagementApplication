package com.epam.rdmanagement.repository;

import com.epam.rdmanagement.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface UserRepository.
 *
 * @author Aishwarya_Bommisetty
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  /**
   * Find by email.
   *
   * @param email: User's email
   * @return the user
   */
  public UserEntity findByEmail(String email);

  /**
   * Find by role role name.
   *
   * @param roleName: role name
   * @return returns the list of Users
   */
  List<UserEntity> findByRoleRoleName(String roleName);
}
