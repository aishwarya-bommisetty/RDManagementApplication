package com.epam.rdmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.rdmanagement.entity.MentorEntity;
import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.entity.StudentEntity;
import com.epam.rdmanagement.entity.TrainerEntity;
import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.exception.AccessDeniedException;
import com.epam.rdmanagement.exception.OperationCouldNotBeProcessed;
import com.epam.rdmanagement.model.UserAddModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.repository.MentorRepository;
import com.epam.rdmanagement.repository.StudentRepository;
import com.epam.rdmanagement.repository.TrainerRepository;
import com.epam.rdmanagement.repository.UserRepository;

/**
 * admin service implementation.
 * 
 * @author Sourabh
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MentorRepository mentorRepository;

	@Autowired
	TrainerRepository trainerRepository;

	@Autowired
	StudentRepository studentRepository;

	@Value("${could-not-add-trainer.message}")
	private String couldNotAddTrainerExceptionMessage;

	@Value("${could-not-add-mentor.message}")
	private String couldNotAddMentorExceptionMessage;

	@Value("${trainer-added.message}")
	private String trainerAddedSuccessfullyMessage;

	@Value("${mentor-added.message}")
	private String mentorAddedSuccessfullyMessage;

	/**
	 * Method will add trainer
	 * 
	 * @param userName its a email id.
	 * @param user     object.
	 * @return status string.
	 * @throws OperationCouldNotBeProcessed
	 */
	@Override
	public String addTrainer(UserAddModel user) throws OperationCouldNotBeProcessed {
		TrainerEntity trainerEntity = new TrainerEntity();
		trainerEntity.setEmail(user.getEmail());
		trainerEntity.setFirstName(user.getFirstName());
		trainerEntity.setLastName(user.getLastName());
		trainerEntity.setPassword(user.getPassword());

		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRoleId(user.getRoleId());
		roleEntity.setRoleName("trainer");
		trainerEntity.setRole(roleEntity);
		trainerRepository.save(trainerEntity);

		if (trainerRepository.findByEmail(user.getEmail()) != null) {
			return trainerAddedSuccessfullyMessage;
		} else {
			throw new OperationCouldNotBeProcessed(couldNotAddTrainerExceptionMessage);
		}
	}

	/**
	 * method will add a new mentor.
	 * 
	 * @param userName its a email id.
	 * @param user     object.
	 * @return status string.
	 * @throws OperationCouldNotBeProcessed
	 */

	@Override
	public String addMentor(UserAddModel user) throws OperationCouldNotBeProcessed {
		MentorEntity mentorEntity = new MentorEntity();
		mentorEntity.setEmail(user.getEmail());
		mentorEntity.setFirstName(user.getFirstName());
		mentorEntity.setLastName(user.getLastName());
		mentorEntity.setPassword(user.getPassword());
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRoleId(user.getRoleId());
		roleEntity.setRoleName("mentor");
		mentorEntity.setRole(roleEntity);
		mentorRepository.save(mentorEntity);

		if (mentorRepository.findByEmail(user.getEmail()) == null) {
			throw new OperationCouldNotBeProcessed(couldNotAddMentorExceptionMessage);

		} else {
			return mentorAddedSuccessfullyMessage;
		}
	}

	/**
	 * method will get all the trainers.
	 * 
	 * @param userName its a email id.
	 * @return lists of user.
	 */
	@Override
	public List<UserProfileModel> getAllTrainers() {
		List<UserProfileModel> userProfileModels = new ArrayList<>();
		List<TrainerEntity> trainerEntities = trainerRepository.findAll();
		for (TrainerEntity trainerEntity : trainerEntities) {
			UserProfileModel userProfileModel = new UserProfileModel();
			userProfileModel.setEmail(trainerEntity.getEmail());
			userProfileModel.setFirstName(trainerEntity.getFirstName());
			userProfileModel.setLastName(trainerEntity.getLastName());
			userProfileModels.add(userProfileModel);
		}
		return userProfileModels;
	}

	/**
	 * method will get all the mentors.
	 * 
	 * @param userName its a email id.
	 * @return lists of user.
	 */
	@Override
	public List<UserProfileModel> getAllMentors() {
		List<UserProfileModel> userProfileModels = new ArrayList<>();
		List<MentorEntity> mentorEntities = mentorRepository.findAll();
		for (MentorEntity mentorEntity : mentorEntities) {
			UserProfileModel userProfileModel = new UserProfileModel();
			userProfileModel.setEmail(mentorEntity.getEmail());
			userProfileModel.setFirstName(mentorEntity.getFirstName());
			userProfileModel.setLastName(mentorEntity.getLastName());
			userProfileModels.add(userProfileModel);
		}
		return userProfileModels;
	}

	/**
	 * method will get all the students.
	 * 
	 * @param userName its a email id.
	 * @return lists of user.
	 */

	@Override
	public List<UserProfileModel> getAllStudents() {
		List<UserProfileModel> userProfileModels = new ArrayList<>();
		List<StudentEntity> studentEntities = studentRepository.findAll();
		for (StudentEntity studentEntity : studentEntities) {
			UserProfileModel userProfileModel = new UserProfileModel();
			userProfileModel.setEmail(studentEntity.getEmail());
			userProfileModel.setFirstName(studentEntity.getFirstName());
			userProfileModel.setLastName(studentEntity.getLastName());
			userProfileModels.add(userProfileModel);
		}
		return userProfileModels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.epam.rdmanagement.service.AdminService#getProfile(java.lang.String)
	 */
	@Override
	public UserProfileModel getProfile(String userEmail) {
		UserEntity userEntity = userRepository.findByEmail(userEmail);
		return new DozerBeanMapper().map(userEntity, UserProfileModel.class);
	}
}
