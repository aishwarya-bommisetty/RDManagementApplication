package com.epam.rdmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.rdmanagement.exception.AccessDeniedException;
import com.epam.rdmanagement.exception.OperationCouldNotBeProcessed;
import com.epam.rdmanagement.model.UserAddModel;
import com.epam.rdmanagement.model.UserProfileModel;

// TODO: Auto-generated Javadoc
/**
 * Admin Service interface.
 * 
 * @author Sourabh
 *
 */
@Service
public interface AdminService {

	/**
	 * Adds the trainer.
	 *
	 * @param userName the user name
	 * @param user     the user
	 * @return the string
	 * @throws AccessDeniedException        the access denied exception
	 * @throws OperationCouldNotBeProcessed the operation could not be processed
	 */
	String addTrainer(UserAddModel user) throws OperationCouldNotBeProcessed;

	/**
	 * Adds the mentor.
	 *
	 * @param userName the user name
	 * @param user     the user
	 * @return the string
	 * @throws AccessDeniedException        the access denied exception
	 * @throws OperationCouldNotBeProcessed the operation could not be processed
	 */
	String addMentor(UserAddModel user) throws OperationCouldNotBeProcessed;

	/**
	 * Gets the all trainers.
	 *
	 * @param userName the user name
	 * @return the all trainers
	 * @throws AccessDeniedException the access denied exception
	 */
	List<UserProfileModel> getAllTrainers();

	/**
	 * Gets the all mentors.
	 *
	 * @param userName the user name
	 * @return the all mentors
	 * @throws AccessDeniedException the access denied exception
	 */
	List<UserProfileModel> getAllMentors();

	/**
	 * Gets the all students.
	 *
	 * @param userName the user name
	 * @return the all students
	 * @throws AccessDeniedException the access denied exception
	 */
	List<UserProfileModel> getAllStudents();

	/**
	 * Gets the profile.
	 *
	 * @param userEmail the user email
	 * @return the profile
	 */
	UserProfileModel getProfile(String userEmail);
}
