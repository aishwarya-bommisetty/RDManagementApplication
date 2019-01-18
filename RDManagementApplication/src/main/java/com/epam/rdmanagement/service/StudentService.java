package com.epam.rdmanagement.service;

import com.epam.rdmanagement.exception.InvalidEmailException;
import com.epam.rdmanagement.exception.NoTrainerAssignedException;
import com.epam.rdmanagement.exception.StudentNotFoundException;
import com.epam.rdmanagement.model.StudentModel;
import com.epam.rdmanagement.model.UserProfileModel;

/**
 * The Interface StudentService for StudentRepository.
 */
public interface StudentService {

    /**
     * Gets the trainer.
     *
     * @param email the email
     * @return the trainer
     * @throws InvalidEmailException      the invalid email exception
     * @throws NoTrainerAssignedException if no trainer is assigned to the student
     */
    UserProfileModel getTrainer(String email) throws InvalidEmailException, NoTrainerAssignedException;

    /**
     * Gets the student by email.
     *
     * @param email the email
     * @return the student by email
     * @throws InvalidEmailException if the email is invalid
     */
    StudentModel getStudentByEmail(String email) throws InvalidEmailException;
    
    StudentModel getProfile(String email) throws StudentNotFoundException, InvalidEmailException;
}