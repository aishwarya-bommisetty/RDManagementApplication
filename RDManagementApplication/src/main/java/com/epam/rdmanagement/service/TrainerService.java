package com.epam.rdmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.rdmanagement.exception.AccessDeniedException;
import com.epam.rdmanagement.exception.MentorNotAvailableException;
import com.epam.rdmanagement.exception.StudentNotFoundException;
import com.epam.rdmanagement.model.TrainerModel;
import com.epam.rdmanagement.model.UserProfileModel;

/**
 * its a trainer service interface.
 * 
 * @author Sourabh
 *
 */
@Service
public interface TrainerService {

	/**
	 * Gets the all mentors.
	 *
	 * @return the all mentors
	 * @throws AccessDeniedException       the access denied exception
	 * @throws MentorNotAvailableException the mentor not available exception
	 */
	List<UserProfileModel> getAllMentors() throws MentorNotAvailableException;

	/**
	 * Gets the all students under trainer.
	 *
	 * @param trainerId the trainer id
	 * @return the all students under trainer
	 * @throws AccessDeniedException the access denied exception
	 */
	List<UserProfileModel> getAllStudentsUnderTrainer(String trainerId) throws StudentNotFoundException;

	/**
	 * Gets the feedback of students under trainer.
	 *
	 * @param trainerId the trainer id
	 * @return the feedback of students under trainer
	 * @throws AccessDeniedException the access denied exception
	 */
//    List<FeedbackStudentModel> getFeedbackOfStudentsUnderTrainer(String trainerId)
//            throws  StudentNotFoundException, FeedBackNotFoundException;

	/**
	 * Checks if is mentor present.
	 *
	 * @return true, if is mentor present
	 */
	boolean isMentorPresent();

	/**
	 * Checks if is student present.
	 *
	 * @return true, if is student present
	 */
	boolean isStudentPresent(String userName);

	/**
	 * Gets the profile.
	 *
	 * @param userEmail the user email
	 * @return the profile
	 */
	TrainerModel getProfile(String userEmail);

}
