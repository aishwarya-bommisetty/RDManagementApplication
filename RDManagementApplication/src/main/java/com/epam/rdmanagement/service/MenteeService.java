package com.epam.rdmanagement.service;

import org.springframework.stereotype.Service;

import com.epam.rdmanagement.exception.MenteeNotFoundException;
import com.epam.rdmanagement.exception.MentorNotAvailableException;
import com.epam.rdmanagement.model.MenteeModel;
import com.epam.rdmanagement.model.UserProfileModel;

/**
 * The Interface MenteeService for MenteeRepository.
 */
@Service
public interface MenteeService {

    /**
     * Gets the mentor.
     *
     * @param email: Mentee's email
     * @return the mentor
     * @throws MentorNotAvailableException if the mentor is not available
     */
    UserProfileModel getMentor(String email) throws MentorNotAvailableException;
    
    /**
     * Gets the mentee by email.
     *
     * @param email: Mentee's email
     * @return the mentee
     * @throws MenteeNotFoundException if the mentee is not found
     */
    MenteeModel getMenteeByEmail(String email) throws MenteeNotFoundException;

	MenteeModel getProfile(String email) throws MenteeNotFoundException;

}