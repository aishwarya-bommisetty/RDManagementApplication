package com.epam.rdmanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.rdmanagement.entity.RoleEntity;
import com.epam.rdmanagement.entity.StudentEntity;
import com.epam.rdmanagement.entity.TrainerEntity;

/**
 * @author Aishwarya_Bommisetty
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryTest {
	/**
	 * TestEntityManager manager dependency.
	 */
	@Autowired
	private TestEntityManager entityManager;

	/**
	 * StudentRepository dependency.
	 */
	@Autowired
	private StudentRepository studentRepository;

	/**
	 * testing the FindAll method of studentRepository
	 */
	StudentEntity student;
	TrainerEntity trainer;
	
	/**
	 * SetUp for tests
	 */
	@Before
	public void setup() {
		student = new StudentEntity();
		student.setFirstName("jack");
		student.setLastName("Ryan");
		student.setPassword("abc");
		RoleEntity role=new RoleEntity();
		role.setRoleId(1);
		role.setRoleName("student");
		entityManager.persist(role);	
		entityManager.flush();
		student.setRole(role);
		student.setEmail("jack_r@epam.com");	
		trainer = new TrainerEntity();
		trainer.setFirstName("jack");
		trainer.setLastName("Ryan");
		trainer.setPassword("abc");
		RoleEntity role2=new RoleEntity();
		role2.setRoleId(4);
		role2.setRoleName("trainer");
		entityManager.persist(role2);	
		entityManager.flush();
		trainer.setRole(role2);
		trainer.setEmail("jack_r@epam.com");
		List<StudentEntity> students = new ArrayList<>();
		students.add(student);
		trainer.setStudents(students);
		entityManager.persist(trainer);
		entityManager.flush();
		student.setTrainer(trainer);
		entityManager.persist(student);
		entityManager.flush();
	}
	
	/**
	 * testing the findByEmail method
	 */
	@Test
	public void testFindByEmail() {
		
		StudentEntity result = studentRepository.findByEmail("jack_r@epam.com");
		assertThat(result.getFirstName()).isEqualTo(student.getFirstName());
	}
	
	/**
	 * testing the findAll method
	 */
	@Test
	public void testFindAll() {
		
		List<StudentEntity> student_list=studentRepository.findAll();
		assertThat(student_list.get(0).getFirstName()).isEqualTo(student.getFirstName());
	}
	
	/**
	 * testing the findByTrainer method
	 */
	@Test
	public void testFindByTrainer() {

		List<StudentEntity> result = studentRepository.findByTrainer(trainer); 
		assertThat(result.get(0).getFirstName()).isEqualTo(student.getFirstName());
	}
	
	/**
	 * testing the getTrainer method
	 */
	@Test
	public void testGetTrainer() {
		
		assertThat(student.getTrainer().getFirstName()).isEqualTo(trainer.getFirstName());
	}
	
	/**
	 * testing the toString method
	 */
	@Test
	public void testToString() {
		assertEquals(student.toString(),"Student [trainer=User [firstName=jack, lastName=Ryan, role=trainer, password=abc]]");
	}
}


