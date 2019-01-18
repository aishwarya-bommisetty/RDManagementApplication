package com.epam.rdmanagement.exception;

/**
 * student not found exception.
 * 
 * @author Sourabh_Deshmukh
 *
 */
public class StudentNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public StudentNotFoundException(String message) {

		super(message);

	}

}
