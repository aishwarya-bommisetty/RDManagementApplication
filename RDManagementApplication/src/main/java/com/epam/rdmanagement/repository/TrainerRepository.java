package com.epam.rdmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.rdmanagement.entity.TrainerEntity;

/**
 * The Interface TrainerRepository for the model Trainer.
 */
public interface TrainerRepository extends JpaRepository<TrainerEntity, Integer> {
	/**
	 * Find by email.
	 * @param trainerId the trainer id
	 * @return the trainer
	 */
	TrainerEntity findByEmail(String trainerId);
}
