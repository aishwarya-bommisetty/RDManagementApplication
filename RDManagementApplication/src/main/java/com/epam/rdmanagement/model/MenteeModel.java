package com.epam.rdmanagement.model;

import java.util.List;

/**
 * The Class MenteeModel.
 */
public class MenteeModel extends StudentModel {

	/** The mentor. */
	private String mentor;

	/** The feedback. */
	private List<FeedbackAddModel> feedback;

	private String trainer;

	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}

	/**
	 * Gets the mentor.
	 *
	 * @return the mentor
	 */
	public String getMentor() {
		return mentor;
	}

	@Override
	public String toString() {
		return "MenteeModel [mentor=" + mentor + ", feedback=" + feedback + ", trainer=" + trainer + "]";
	}

	/**
	 * Sets the mentor.
	 *
	 * @param mentor the new mentor
	 */
	public void setMentor(String mentor) {
		this.mentor = mentor;
	}

	/**
	 * Gets the feedback.
	 *
	 * @return the feedback
	 */
	public List<FeedbackAddModel> getFeedback() {
		return feedback;
	}

	/**
	 * Sets the feedback.
	 *
	 * @param feedback the new feedback
	 */
	public void setFeedback(List<FeedbackAddModel> feedback) {
		this.feedback = feedback;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((feedback == null) ? 0 : feedback.hashCode());
		result = prime * result + ((mentor == null) ? 0 : mentor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenteeModel other = (MenteeModel) obj;
		if (feedback == null) {
			if (other.feedback != null)
				return false;
		} else if (!feedback.equals(other.feedback))
			return false;
		if (mentor == null) {
			if (other.mentor != null)
				return false;
		} else if (!mentor.equals(other.mentor))
			return false;
		return true;
	}

}
