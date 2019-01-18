package com.epam.rdmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.exception.AccessDeniedException;
import com.epam.rdmanagement.exception.AdminNotFoundException;
import com.epam.rdmanagement.exception.OperationCouldNotBeProcessed;
import com.epam.rdmanagement.model.UserAddModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.repository.UserRepository;

/**
 * Super admin service interface implementation.
 * 
 * @author Sourabh
 *
 */
@Service
public class SuperAdminServiceImpl implements SuperAdminService {

	/** The user repository. */
	@Autowired
	UserRepository userRepository;

	/** The could not add admin exception message. */
	@Value("${could-not-add-admin.message}")
	private String couldNotAddAdminExceptionMessage;

	@Value("${admin-not-foound.message}")
	private String adminNotFoundExceptionMessage;

	@Value("${admin-added-successfully.message}")
	private String adminAddedSuccessfullyMessage;

	@Value("${admin.role-name}")
	private String adminRoleName;

	/**
	 * it will list all the admins.
	 *
	 * @param userName the user name
	 * @return it will return list of admins.
	 * @throws AdminNotFoundException
	 * @throws AccessDeniedException  the access denied exception
	 */
	@Override
	public List<UserProfileModel> getAllAdmins() throws AdminNotFoundException {
		List<UserProfileModel> userProfileModels = new ArrayList<>();
		List<UserEntity> userEntities = userRepository.findByRoleRoleName(adminRoleName);
		if (!userEntities.isEmpty()) {
			for (UserEntity userEntity : userEntities) {
				UserProfileModel userProfileModel = new UserProfileModel();
				userProfileModel.setEmail(userEntity.getEmail());
				userProfileModel.setFirstName(userEntity.getFirstName());
				userProfileModel.setLastName(userEntity.getLastName());
				userProfileModels.add(userProfileModel);
			}
			return userProfileModels;
		} else {
			throw new AdminNotFoundException(adminNotFoundExceptionMessage);
		}
	}

	/**
	 * it will add admin.
	 *
	 * @param userName the user name
	 * @param user     its a user object.
	 * @return it will string.
	 * @throws AccessDeniedException        the access denied exception
	 * @throws OperationCouldNotBeProcessed the operation could not be processed
	 */
	@Override
	public String addAdmin(UserAddModel userAddModel) throws OperationCouldNotBeProcessed {
		UserEntity userEntity = new UserEntity();
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRoleId(userAddModel.getRoleId());
		roleEntity.setRoleName(adminRoleName);
		userEntity.setRole(roleEntity);
		userEntity.setEmail(userAddModel.getEmail());
		userEntity.setFirstName(userAddModel.getFirstName());
		userEntity.setLastName(userAddModel.getLastName());
		userEntity.setPassword(userAddModel.getPassword());
		userRepository.save(userEntity);
		if (userRepository.findByEmail(userAddModel.getEmail()) == null) {
			throw new OperationCouldNotBeProcessed(couldNotAddAdminExceptionMessage);
		} else {
			return adminAddedSuccessfullyMessage;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.epam.rdmanagement.service.SuperAdminService#getProfile(java.lang.String)
	 */
	@Override
	public UserProfileModel getProfile(String userEmail) {
		UserEntity userEntity = userRepository.findByEmail(userEmail);
		return new DozerBeanMapper().map(userEntity, UserProfileModel.class);
	}
}
