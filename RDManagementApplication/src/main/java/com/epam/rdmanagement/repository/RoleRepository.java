package com.epam.rdmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.rdmanagement.entity.RoleEntity;

/**
 * The Interface RoleRepository for the model Role.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {

}
