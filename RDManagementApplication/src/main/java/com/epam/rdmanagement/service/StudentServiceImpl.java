package com.epam.rdmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.epam.rdmanagement.entity.StudentEntity;
import com.epam.rdmanagement.entity.TrainerEntity;
import com.epam.rdmanagement.exception.InvalidEmailException;
import com.epam.rdmanagement.exception.NoTrainerAssignedException;
import com.epam.rdmanagement.exception.StudentNotFoundException;
import com.epam.rdmanagement.model.StudentModel;
import com.epam.rdmanagement.model.TrainerModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.repository.StudentRepository;

/**
 * The Class StudentServiceImpl.
 */
@Service
@Primary
public class StudentServiceImpl implements StudentService {

    /** The student repository. */
    @Autowired
    private StudentRepository studentRepository;

    @Value("${invalid-email-exception-message}")
    private String invalidEmailExceptionMessage;
    
    @Value("${no-trainer-assigned-exception-message}")
    private String noTrainerAssignedExceptionMessage;
    
    @Override
    public StudentModel getStudentByEmail(String email) throws InvalidEmailException {
        StudentModel model = new StudentModel();
        StudentEntity student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new InvalidEmailException(invalidEmailExceptionMessage);
        }
        return getStudentModel(model, student);
    }

	private StudentModel getStudentModel(StudentModel model, StudentEntity student) {
		model.setEmail(student.getEmail());
        model.setFirstName(student.getFirstName());
        model.setLastName(student.getLastName());
        model.setTrainer(student.getTrainer().getFirstName());
        return model;
	}

    @Override
    public UserProfileModel getTrainer(String email) throws InvalidEmailException, NoTrainerAssignedException {
        StudentEntity student = studentRepository.findByEmail(email);
        TrainerEntity trainer = student.getTrainer();
        if (trainer == null) {
            throw new NoTrainerAssignedException(noTrainerAssignedExceptionMessage);
        }
        return getUserProfileModel(trainer);
    }

	private UserProfileModel getUserProfileModel(TrainerEntity trainer) {
		UserProfileModel userModel = new TrainerModel();
        userModel.setFirstName(trainer.getFirstName());
        userModel.setLastName(trainer.getLastName());
        userModel.setEmail(trainer.getEmail());
        return userModel;
	}

	@Override
	public StudentModel getProfile(String email) throws StudentNotFoundException, InvalidEmailException {
		
		return getStudentByEmail(email);
	}

}
