package com.epam.rdmanagement.exception;

/**
 * 
 * @author Prabhudeep_Banga
 *
 */
public class AccessDeniedException extends Exception {

    private static final long serialVersionUID = 63392433375726994L;

    public AccessDeniedException(String exceptionMessage) {
        
		super(exceptionMessage);
	}

}
