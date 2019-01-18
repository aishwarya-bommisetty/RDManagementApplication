package com.epam.rdmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.rdmanagement.exception.MenteesNotFoundException;
import com.epam.rdmanagement.model.MenteeModel;
import com.epam.rdmanagement.model.MentorModel;

/**
 *
 * @author Prabhudeep_Banga
 */
@Service
public interface MentorService {

	/**
	 * Gets the all mentees under mentor.
	 *
	 * @param trainerId the id of the trainer.
	 * @return the list of all the mentees under mentor.
	 * @throws MenteesNotFoundException 
	 */
	List<MenteeModel> getAllMenteesUnderMentor(String mentorEmail) throws MenteesNotFoundException;
	
    /**
     * Gets the profile of a mentor.
     *
     * @param mentorEmail email ID of mentor
     * @return the profile of the mentor
     */
    MentorModel getProfile(String mentorEmail);

}
