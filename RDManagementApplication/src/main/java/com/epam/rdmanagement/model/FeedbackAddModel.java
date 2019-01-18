package com.epam.rdmanagement.model;

import com.epam.rdmanagement.entity.FeedbackEntity;

/**
 * 
 * @author Prabhudeep_Banga
 *
 */
public class FeedbackAddModel {

    private String fromUserEmail;

    private String toUserEmail;

    private String feedback;

    /**
     * Gets the from user email.
     *
     * @return the from user email
     */
    
    public String getFromUserEmail() {
        return fromUserEmail;
    }

    @Override
	public String toString() {
		return "FeedbackAddModel [fromUserEmail=" + fromUserEmail + ", toUserEmail=" + toUserEmail + ", feedback="
				+ feedback + "]";
	}

	public FeedbackAddModel() {
    	
    }
    public FeedbackAddModel(FeedbackEntity feedbackEntity) {
		setFromUserEmail(feedbackEntity.getFromUser().getEmail());
		setToUserEmail(feedbackEntity.getToUser().getEmail());
		setFeedback(feedbackEntity.getFeedback());
	}

	/**
     * Sets the from user email.
     *
     * @param fromUserEmail the new from user email
     */
    public void setFromUserEmail(String fromUserEmail) {
        this.fromUserEmail = fromUserEmail;
    }

    /**
     * Gets the to user email.
     *
     * @return the to user email
     */
    public String getToUserEmail() {
        return toUserEmail;
    }

    /**
     * Sets the to user email.
     *
     * @param toUserEmail the new to user email
     */
    public void setToUserEmail(String toUserEmail) {
        this.toUserEmail = toUserEmail;
    }

    /**
     * Gets the feedback.
     *
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback.
     *
     * @param feedback the new feedback
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
