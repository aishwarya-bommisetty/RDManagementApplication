package com.epam.rdmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.rdmanagement.entity.StudentEntity;
import com.epam.rdmanagement.entity.TrainerEntity;

/**
 * The Interface StudentRepository for the model Student.
 */
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

	/**
	 * Find Student by email.
	 * @param email: Student's email
	 * @return the student
	 */
	public StudentEntity findByEmail(String email);
	
	/**
	 * Find by trainer.
	 * @param trainer: the trainer
	 * @return the list of students under that trainer
	 */
	public List<StudentEntity> findByTrainer(TrainerEntity trainer);
}
