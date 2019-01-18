package com.epam.rdmanagement.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.rdmanagement.entity.StudentEntity;
import com.epam.rdmanagement.entity.TrainerEntity;
import com.epam.rdmanagement.exception.InvalidEmailException;
import com.epam.rdmanagement.exception.NoTrainerAssignedException;
import com.epam.rdmanagement.exception.StudentNotFoundException;
import com.epam.rdmanagement.model.StudentModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.repository.StudentRepository;

@RunWith(SpringRunner.class)
@PropertySource("classpath: application-test.properties")
public class StudentServiceImplTest {

    /**
     * Constants.
     * @author Aishwarya_Bommisetty
     *
     */
    @Value("${email}")
    private String email;
    
    @Value("${invalid-email}")
    private String invalidEmail;
    
    @Value("${invalid-email0}")
    private String invalidEmail0;
    
    @Value("${first-name}")
    private String firstName;
    
    @Value("${invalid-first-name}")
    private String invalidFirstName;
    
    @Value("${last-name}")
    private String lastName;
    
    @Value("${invalid-last-name}")
    private String invalidLastName;
    
    @Value("${password}")
    private String password;
    
    @Value("${invalid-email-exception-message}")
    private String invalidEmailExceptionMessage;
    
    @Value("${no-trainer-assigned-exception-message}")
    private String noTrainerAssignedExceptionMessage;
    
	@TestConfiguration
	static class StudentServiceImplTestContextConfiguration {
		@Bean
		public StudentService studentService() {
			return new StudentServiceImpl();
		}
	}

	@Autowired
	private StudentService studentService;

	@MockBean
	private StudentRepository studentRepository;

	private StudentEntity student;
	private StudentEntity student1;
    private TrainerEntity trainer;
    private TrainerEntity trainer1;
	@Before
	public void setUp() {
		student = new StudentEntity();
		student.setEmail(email);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setPassword(password);
		
		trainer = new TrainerEntity();
		trainer.setFirstName("jacob");
		trainer.setLastName("abercrombie");
		trainer.addStudent(student);
		
		student.setTrainer(trainer);
		student1 = new StudentEntity();
		student1.setEmail(invalidEmail);
		student1.setFirstName(invalidFirstName);
		student1.setLastName(invalidLastName);
		student1.setPassword(password);
		List<StudentEntity> students = new ArrayList<>();
		students.add(student);
		Mockito.when(studentRepository.findByEmail(student.getEmail()+"@epam.com")).thenReturn(student);
		Mockito.when(studentRepository.findByEmail(student1.getEmail()+"@epam.com")).thenReturn(student1);
		Mockito.when(studentRepository.findByTrainer(trainer)).thenReturn(students);
		Mockito.when(studentRepository.findByTrainer(trainer1)).thenReturn(null);
	}

	@Test
	public void testFindValidStudent() throws InvalidEmailException {
		StudentModel result = studentService.getStudentByEmail(email);
		assertThat(result.getEmail()).isEqualTo(email);
	}

	@Test
	public void testFindInvalidStudent() throws InvalidEmailException {
		try {
		studentService.getStudentByEmail(invalidEmail0);
		} catch (InvalidEmailException e) {
			assertThat(e.getMessage()).isEqualTo(invalidEmailExceptionMessage);
		}
		
	}

	@Test
	public void testGetTrainer() throws InvalidEmailException, NoTrainerAssignedException {
		UserProfileModel result = studentService.getTrainer(email);
		assertThat(result.getFirstName()).isEqualTo(trainer.getFirstName());
	}

	@Test
	public void testNoTrainerAssignedForGetTrainer() throws InvalidEmailException, NoTrainerAssignedException {
		try {
		studentService.getTrainer(invalidEmail);
		} catch (NoTrainerAssignedException e) {
			assertThat(e.getMessage()).isEqualTo(noTrainerAssignedExceptionMessage);
		}
	}

	@Test
	public void testGetProfile() throws StudentNotFoundException, InvalidEmailException {
		StudentModel result = studentService.getProfile(email);
		StudentModel expected = new StudentModel();
		expected.setEmail(email);
		expected.setFirstName(firstName);
		expected.setLastName("abercrombie");
		assertThat(result.getEmail()).isEqualTo(expected.getEmail());
	}
	
}
