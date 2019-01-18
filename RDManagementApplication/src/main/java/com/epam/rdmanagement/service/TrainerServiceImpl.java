package com.epam.rdmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.rdmanagement.entity.StudentEntity;
import com.epam.rdmanagement.entity.TrainerEntity;
import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.exception.MentorNotAvailableException;
import com.epam.rdmanagement.exception.StudentNotFoundException;
import com.epam.rdmanagement.model.TrainerModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.repository.FeedbackRepository;
import com.epam.rdmanagement.repository.TrainerRepository;
import com.epam.rdmanagement.repository.UserRepository;

/**
 * Trainer services implementation class.
 * 
 * @author Sourabh
 *
 */
@Service
public class TrainerServiceImpl implements TrainerService {

	/** The user repository. */
	@Autowired
	UserRepository userRepository;

	/** The trainer repository. */
	@Autowired
	TrainerRepository trainerRepository;

	/** The feedback repository. */
	@Autowired
	FeedbackRepository feedbackRepository;

	/** The mentor not available exception message. */
	@Value("${mentor-not-available-exception.message}")
	private String mentorNotAvailableExceptionMessage;

	@Value("${student-not-found-exception.message}")
	private String studentNotFoundExceptionMessage;
	
	@Value("${mentor.role-name}")
	private String mentorRoleName;

	@Override
	public List<UserProfileModel> getAllMentors() throws MentorNotAvailableException {
		List<UserProfileModel> userProfileModels = new ArrayList<>();
		if (isMentorPresent()) {
			List<UserEntity> userEntities = userRepository.findByRoleRoleName(mentorRoleName);
			for (UserEntity userEntity : userEntities) {
				UserProfileModel userProfileModel = new UserProfileModel();
				userProfileModel.setEmail(userEntity.getEmail());
				userProfileModel.setFirstName(userEntity.getFirstName());
				userProfileModel.setLastName(userEntity.getLastName());
				userProfileModels.add(userProfileModel);
			}
			return userProfileModels;
		} else {
			throw new MentorNotAvailableException(mentorNotAvailableExceptionMessage);
		}
	}

	public boolean isMentorPresent() {
		return !(userRepository.findByRoleRoleName(mentorRoleName).isEmpty());
	}

	@Override
	public List<UserProfileModel> getAllStudentsUnderTrainer(String userName) throws StudentNotFoundException {
		List<UserProfileModel> userProfileModels = new ArrayList<>();
		if (isStudentPresent(userName)) {
			List<StudentEntity> studentEntities = trainerRepository.findByEmail(userName + "@epam.com").getStudents();

			for (StudentEntity studentEntity : studentEntities) {
				UserProfileModel userProfileModel = new UserProfileModel();
				userProfileModel.setEmail(studentEntity.getEmail());
				userProfileModel.setFirstName(studentEntity.getFirstName());
				userProfileModel.setLastName(studentEntity.getLastName());
				userProfileModels.add(userProfileModel);
			}
			return userProfileModels;
		} else {
			throw new StudentNotFoundException(studentNotFoundExceptionMessage);
		}
	}


	@Override
	public boolean isStudentPresent(String userName) {
		return !(trainerRepository.findByEmail(userName).getStudents()).isEmpty();
	}

	@Override
	public TrainerModel getProfile(String userEmail) {
		TrainerEntity trainerEntity = trainerRepository.findByEmail(userEmail);
		return new DozerBeanMapper().map(trainerEntity, TrainerModel.class);
	}

}
