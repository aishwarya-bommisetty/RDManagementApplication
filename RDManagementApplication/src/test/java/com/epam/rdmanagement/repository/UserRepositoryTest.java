package com.epam.rdmanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.entity.StudentEntity;
import com.epam.rdmanagement.entity.UserEntity;

/**
 * The Class UserRepositoryTest.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	/**
	 * TestEntityManager manager dependency.
	 */
	@Autowired
	private TestEntityManager entityManager;

	/**
	 * TrainerRepository dependency.
	 */
	@Autowired
	private UserRepository userRepository;
	
	/** The user. */
	UserEntity user;
	
	/**
	 * SetUp for tests
	 */
	@Before
	public void SetUp() {
		user = new StudentEntity();
		user.setFirstName("jack");
		user.setLastName("Ryan");
		user.setPassword("abc");
		RoleEntity role=new RoleEntity();
		role.setRoleId(1);
		role.setRoleName("student");
		entityManager.persist(role);	
		entityManager.flush();
		user.setRole(role);
		user.setEmail("jack_r@epam.com");
		entityManager.persist(user);
		entityManager.flush();
	}
	
	/**
	 * test for findByEmail method.
	 */
	@Test
	public void testFindByEmail() {
		assertEquals(userRepository.findByEmail("jack_r@epam.com").getLastName(),user.getLastName());
	}

	/**
	 * test for trying to find a non-existing user by email.
	 */
	@Test
	public void testUserDoesntExist()
	{
		assertEquals(userRepository.findByEmail("jack_ma@epam.com"),null);
	}
	
	/**
	 * Test find by user id.
	 */
	@Test
	public void testFindByUserId() {
		assertThat(userRepository.findById(user.getUserId()).get()).isNotEqualTo(null);
	}
	
	/**
	 * Test negative case for findByUserId.
	 */
	@Test
	public void testNegativeCaseFindByUserId() {
		assertThat(userRepository.findById(35)).isEmpty();
	}
	
	/**
	 * Testing findById method.
	 */
	@Test
	public void testFindById() {
		assertThat(userRepository.findById(user.getUserId()).get().getEmail()).isEqualTo("jack_r@epam.com");
	}
	
	/**
	 * Testing findPasswordMethod
	 */
	@Test
	public void testFindPassword() {
		assertThat(userRepository.findByEmail("jack_r@epam.com").getPassword()).isEqualTo("abc");
	}
}
