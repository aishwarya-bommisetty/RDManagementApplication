package com.epam.rdmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.entity.UserEntity;
import com.epam.rdmanagement.exception.AccessDeniedException;
import com.epam.rdmanagement.exception.AdminNotFoundException;
import com.epam.rdmanagement.exception.OperationCouldNotBeProcessed;
import com.epam.rdmanagement.model.UserAddModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.repository.UserRepository;

/**
 * test cases for super admin services.
 * 
 * @author Sourabh
 *
 */
@RunWith(SpringRunner.class)
public class SuperAdminServiceTestCase {

	@TestConfiguration
	static class SuperAdminServiceImplTestContextConfiguration {

		@Bean
		public SuperAdminService SuperAdminService() {
			return new SuperAdminServiceImpl();
		}
	}

	@Autowired
	private SuperAdminService superAdminService;

	@MockBean
	private UserRepository userRepository;

	private RoleEntity role;
	private UserEntity userEntity;
	private UserAddModel userAddModel;
	private UserProfileModel userProfileModel;
	private List<UserProfileModel> adminList;
	private List<UserEntity> adminEntityList;
	private UserProfileModel superAdminProfileModel;
	private DozerBeanMapper beanMapper;

	@Value("${admin-added-successfully.message}")
	private String adminAddedSuccessfullyMessage;

	@Value("${email}")
	private String email;

	@Value("${first-name}")
	private String firstName;

	@Value("${last-name}")
	private String lastName;

	@Value("${password}")
	private String password;

	@Value("${admin.role-name}")
	private String adminRoleName;

	@Before
	public void setUp() {
		role = new RoleEntity();
		role.setRoleId(2);
		role.setRoleName(adminRoleName);
		userEntity = new UserEntity();
		userEntity.setEmail(email);
		userEntity.setFirstName(firstName);
		userEntity.setLastName(lastName);
		userEntity.setPassword(password);
		userEntity.setRole(role);

		beanMapper = new DozerBeanMapper();
		superAdminProfileModel = beanMapper.map(userEntity, UserProfileModel.class);

		userAddModel = new UserAddModel();
		userAddModel.setEmail(email);
		userAddModel.setFirstName(firstName);
		userAddModel.setLastName(lastName);
		userAddModel.setPassword(password);
		userAddModel.setRoleId(2);

		userProfileModel = new UserProfileModel();
		userProfileModel.setEmail(email);
		userProfileModel.setFirstName(firstName);
		userProfileModel.setLastName(lastName);

		adminList = new ArrayList<>();
		adminList.add(userProfileModel);

		adminEntityList = new ArrayList<>();
		adminEntityList.add(userEntity);
	}

	/**
	 * test case when admin not found.
	 * 
	 * @throws AccessDeniedException
	 * @throws AdminNotFoundException
	 */
	@Test(expected = AdminNotFoundException.class)
	public void testNoAdminFound() throws AdminNotFoundException {
		adminEntityList.clear();
		Mockito.when(userRepository.findByRoleRoleName(adminRoleName)).thenReturn(adminEntityList);
		superAdminService.getAllAdmins();
	}

	/**
	 * test case to check get all admin.
	 * 
	 * @throws AccessDeniedException
	 * @throws AdminNotFoundException
	 */
	@Test
	public void testGetAllAdminsResult() throws AdminNotFoundException {
		Mockito.when(userRepository.findByRoleRoleName(adminRoleName)).thenReturn(adminEntityList);
		List<UserProfileModel> userProfileModels = superAdminService.getAllAdmins();
		assertThat(userProfileModels.get(0).getEmail()).isEqualTo(adminList.get(0).getEmail());
	}

	/**
	 * test case to check success of add admin.
	 * 
	 * @throws AccessDeniedException
	 * @throws OperationCouldNotBeProcessed
	 */
	@Test
	public void testAddAdminSucceed() throws OperationCouldNotBeProcessed {
		Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);
		Mockito.when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(userEntity);
		String successResult = superAdminService.addAdmin(userAddModel);
		assertThat(successResult).isEqualTo(adminAddedSuccessfullyMessage);
	}

	/**
	 * 
	 * /** test case to check failure of add admin.
	 * 
	 * @throws AccessDeniedException
	 * @throws OperationCouldNotBeProcessed
	 */
	@Test(expected = OperationCouldNotBeProcessed.class)
	public void testAddAdminFails() throws OperationCouldNotBeProcessed {
		Mockito.when(userRepository.save(userEntity)).thenReturn(null);
		superAdminService.addAdmin(userAddModel);
	}

	@Test
	public void testGetProfile() {
		Mockito.when(userRepository.findByEmail(email)).thenReturn(userEntity);
		UserProfileModel resultUserModel = superAdminService.getProfile(email);
		assertThat(resultUserModel).isEqualTo(superAdminProfileModel);
	}

}
