package com.epam.rdmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.rdmanagement.exception.AccessDeniedException;
import com.epam.rdmanagement.exception.AdminNotFoundException;
import com.epam.rdmanagement.exception.OperationCouldNotBeProcessed;
import com.epam.rdmanagement.model.UserAddModel;
import com.epam.rdmanagement.model.UserProfileModel;

// TODO: Auto-generated Javadoc
/**
 * Super admin service interface.
 * 
 * @author Sourabh
 *
 */
@Service
public interface SuperAdminService {

	/**
	 * Gets the all admins.
	 *
	 * @param userName the user name
	 * @return the all admins
	 * @throws AccessDeniedException the access denied exception
	 */
	List<UserProfileModel> getAllAdmins() throws AdminNotFoundException;

	/**
	 * Adds the admin.
	 *
	 * @param userName the user name
	 * @param user     the user
	 * @return the string
	 * @throws AccessDeniedException        the access denied exception
	 * @throws OperationCouldNotBeProcessed the operation could not be processed
	 */
	String addAdmin(UserAddModel userAddModel) throws OperationCouldNotBeProcessed;

	/**
	 * Gets the profile.
	 *
	 * @param userEmail the user email
	 * @return the profile
	 */
	UserProfileModel getProfile(String userEmail);
}
