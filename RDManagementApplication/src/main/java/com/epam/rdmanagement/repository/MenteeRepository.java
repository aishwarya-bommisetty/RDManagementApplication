package com.epam.rdmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.rdmanagement.entity.MenteeEntity;

/**
 * The Interface MenteeRepository for model Mentee
 * @author Aishwarya_Bommisetty
 */

@Repository
public interface MenteeRepository extends JpaRepository<MenteeEntity, Integer> {

	/**
	 * Find mentee by email.
	 * @param email of the mentee
	 * @return the mentee
	 */
	public MenteeEntity findByEmail(String email);
}
