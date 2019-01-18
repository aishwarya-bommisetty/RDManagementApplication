package com.epam.rdmanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

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

@RunWith(SpringRunner.class)
@DataJpaTest
public class MentorRepositoryTest {

	/**
	 * TestEntityManager manager dependency.
	 */
	@Autowired
	private TestEntityManager entityManager;

	/**
	 * FeedbackRepository dependency.
	 */
	@Autowired
	private MentorRepository mentorRepository;

	MentorEntity mentor;
	MenteeEntity mentee;
	
	/**
	 * SetUp for tests.
	 */
	@Before
	public void setUp() {
		mentor = new MentorEntity();
		mentor.setEmail("thomas_cook@epam.com");
		mentee = new MenteeEntity();
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
		
	}
	
	/**
	 * testing the findByEmail method
	 */
	@Test
	public void testFindByEmail() {
		MentorEntity result = mentorRepository.findByEmail("thomas_cook@epam.com");
		assertThat(result).isEqualTo(mentor);
	}
	
	/**
	 * testing the getMentee method
	 */
	@Test
	public void testGetMentee() {
		assertThat(mentor.getMentees().get(0)).isEqualTo(mentee);
	}
	
	/**
	 * testing the getMentees method
	 */
	@Test
	public void testGetMentees() {
		MenteeEntity new_mentee = new MenteeEntity();
		List<MenteeEntity> mentees = new ArrayList<>();
		mentees.add(new_mentee);
		mentor.setMentees(mentees);
		assertThat(mentor.getMentees().get(0)).isEqualTo(new_mentee);
	}

	/**
	 * testing the removeMentee method
	 */
	@Test
	public void testRemoveMentee()
	{
		mentor.removeMentee(mentee);
		assertThat(mentor.getMentees().size()).isEqualTo(0);
	}
}
