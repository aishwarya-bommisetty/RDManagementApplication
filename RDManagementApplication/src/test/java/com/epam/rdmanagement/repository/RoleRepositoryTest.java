package com.epam.rdmanagement.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.entity.StudentEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

	/**
	 * TestEntityManager manager dependency.
	 */
	@Autowired
	private TestEntityManager entityManager;

	/**
	 * RoleRepository dependency.
	 */
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Reference of Student class
	 */
	StudentEntity student;

	/**
	 * SetUp for tests
	 */
	@Before
	public void setUp() {
		student = new StudentEntity();
		student.setFirstName("jack");
		student.setLastName("Ryan");
		student.setPassword("abc");
		RoleEntity role = new RoleEntity();
		role.setRoleId(1);
		role.setRoleName("student");
		entityManager.persist(role);
		entityManager.flush();
		student.setRole(role);
		student.setEmail("jack_r@epam.com");
		entityManager.persist(student);
	}

	/**
	 * testing the getRoleId() method
	 */
	@Test
	public void testGetRoleId() {
		RoleEntity result = student.getRole();
		assertEquals(result.getRoleId(), roleRepository.findById(1).get().getRoleId());
	}

}
