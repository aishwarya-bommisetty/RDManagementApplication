package com.epam.rdmanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.rdmanagement.entity.MenteeEntity;
import com.epam.rdmanagement.entity.MentorEntity;
import com.epam.rdmanagement.entity.RoleEntity;

/**
 * The Class MenteeRepositoryTest.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class MenteeRepositoryTest {

	/**
	 * TestEntityManager manager dependency.
	 */
	@Autowired
	private TestEntityManager entityManager;

	/**
	 * MenteeRepository dependency.
	 */
	@Autowired
	private MenteeRepository menteeRepository;

	/** The mentee. */
	MenteeEntity mentee;
	
	/** The mentor. */
	MentorEntity mentor;
	
	/**
	 * SetUp for tests
	 */
	@Before
	public void SetUp() {
		mentee = new MenteeEntity();
		mentor = new MentorEntity();
		mentee.setFirstName("jack");
		mentee.setLastName("Ryan");
		mentee.setPassword("abc");
		RoleEntity role = new RoleEntity();
		role.setRoleId(2);
		role.setRoleName("mentee");
		entityManager.persist(role);
		entityManager.flush();
		mentee.setRole(role);
		mentee.setEmail("jack_r@epam.com");
		entityManager.persist(mentee);
		entityManager.flush();
		mentor.addMentee(mentee);
		entityManager.persist(mentor);
		entityManager.flush();
	}

	/**
	 * Test find by email.
	 */
	@Test
	public void testFindByEmail() {
		String email = "jack_r@epam.com";
		MenteeEntity result = menteeRepository.findByEmail(email);
		assertThat(result).isEqualTo(mentee);
	}

	/**
	 * Test find mentor.
	 */
	@Test
	public void testFindMentor() {
		MentorEntity result = mentee.getMentor();
		assertThat(result).isEqualTo(mentor);

	}
	
}
