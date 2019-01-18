package com.epam.rdmanagement.service;

import com.epam.rdmanagement.exception.OperationCouldNotBeProcessed;
import com.epam.rdmanagement.model.FeedbackAddModel;
import com.epam.rdmanagement.model.RoleModel;

// TODO: Auto-generated Javadoc
/**
 * The Interface ProfileService.
 *
 * @author Prabhudeep_Banga
 */
public interface ProfileService {

    /**
     * Gets the role of the user.
     *
     * @param userEmail the user email
     * @return the role of the user.
     */
    RoleModel getRole(String userEmail);
    
    /**
     * Adds a feedback.
     *
     * @param feedbackAddModel the feedback add model
     * @return true, if successful
     *         false, if not successful
     * @throws OperationCouldNotBeProcessed
     */
    boolean giveFeedback(FeedbackAddModel feedbackAddModel) throws OperationCouldNotBeProcessed;
}
