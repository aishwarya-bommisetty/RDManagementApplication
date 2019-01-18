package com.epam.rdmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.rdmanagement.entity.FeedbackEntity;
import com.epam.rdmanagement.entity.MenteeEntity;
import com.epam.rdmanagement.entity.MentorEntity;
import com.epam.rdmanagement.exception.MenteeNotFoundException;
import com.epam.rdmanagement.exception.MentorNotAvailableException;
import com.epam.rdmanagement.model.FeedbackAddModel;
import com.epam.rdmanagement.model.MenteeModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.repository.MenteeRepository;
import com.epam.rdmanagement.util.ConstantsUtil;

/**
 * The Class MenteeServiceImpl.
 */

@Service
public class MenteeServiceImpl extends StudentServiceImpl implements MenteeService {

	/** The mentee repository. */
	@Autowired
	private MenteeRepository menteeRepository;

	@Override
	public MenteeModel getMenteeByEmail(String email) throws MenteeNotFoundException {

		MenteeEntity result = menteeRepository.findByEmail(email);
		if (result == null) {
			throw new MenteeNotFoundException(ConstantsUtil.MENTEENOTFOUNDEXCEPTIONMESSAGE);
		}
		return getMenteeModel(result);
	}


	private MenteeModel getMenteeModel(MenteeEntity mentee) {
		MenteeModel model = new MenteeModel();
		model.setFirstName(mentee.getFirstName());
		model.setLastName(mentee.getLastName());
		model.setEmail(mentee.getEmail());
		model.setMentor(mentee.getMentor().getFirstName() + " " + mentee.getMentor().getLastName());
		model.setTrainer(mentee.getTrainer().getFirstName()+" "+mentee.getTrainer().getLastName());
		model.setFeedback(getFeedback(mentee));
		return model;
	}

	private List<FeedbackAddModel> getFeedback(MenteeEntity mentee) {
		System.out.println(mentee.getEmail());
		List<FeedbackEntity> feedbacks = mentee.getRecievedFeedbacks();
		List<FeedbackAddModel> feedbackModel = new ArrayList<>();
		System.out.println(feedbacks);
		for(FeedbackEntity feed : feedbacks)
		{
			feedbackModel.add(new FeedbackAddModel(feed));
		}
		return feedbackModel;
	}


	@Override
	public MenteeModel getProfile(String email) throws MenteeNotFoundException {
		return getMenteeByEmail(email);
	}
	
	
	@Override
	public UserProfileModel getMentor(String email) throws MentorNotAvailableException {
		MenteeEntity mentee = menteeRepository.findByEmail(email);
		MentorEntity mentor = mentee.getMentor();
		if (mentor == null) {
			throw new MentorNotAvailableException(ConstantsUtil.MENTORNOTAVAILABLEEXCEPTIONMESSAGE);
		}
		return getUserProfileModel(mentor);
	}

	private UserProfileModel getUserProfileModel(MentorEntity mentor) {
		UserProfileModel model = new UserProfileModel();
		model.setEmail(mentor.getEmail());
		model.setFirstName(mentor.getFirstName());
		model.setLastName(mentor.getLastName());
		return model;
	}

}
