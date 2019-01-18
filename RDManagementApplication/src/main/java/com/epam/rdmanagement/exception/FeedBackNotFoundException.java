package com.epam.rdmanagement.exception;

/**
 * The Class FeedBackNotFoundException.
 */
public class FeedBackNotFoundException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new feed back not found exception.
	 *
	 * @param message the message
	 */
	public FeedBackNotFoundException(String message) {
		super(message);
	}

}
