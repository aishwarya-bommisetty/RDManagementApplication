package com.epam.rdmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.rdmanagement.entity.MentorEntity;

/**
 * The Interface MentorRepository for the model Mentor
 */
@Repository
public interface MentorRepository extends JpaRepository<MentorEntity, Integer>{

	/**
	 * Find mentor by email.
	 *
	 * @param email: Mentor's email
	 * @return the mentor
	 */
	public MentorEntity findByEmail(String email);
}
