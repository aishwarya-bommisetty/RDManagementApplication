package com.epam.rdmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.rdmanagement.exception.InvalidEmailException;
import com.epam.rdmanagement.exception.NoTrainerAssignedException;
import com.epam.rdmanagement.exception.StudentNotFoundException;
import com.epam.rdmanagement.model.StudentModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.service.StudentService;

@RestController
@RequestMapping("/profile")
public class StudentController {

    /**
     * Autowire student service.
     */
    @Autowired
    StudentService studentService;
    
    /**
     * Epam email extension.
     */
    @Value("${epam-email-extension}")
    private String epamEmailExtension;
    
    /**
     * Get profile of a student.
     * 
     * @param userName Username of the student.
     * @return Returns all the profile details related to a user.
     */
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("student/{userName}")
    public ResponseEntity<StudentModel> getStudentProfile(@PathVariable String userName) {
    	try {
        return ResponseEntity.ok().body(studentService.getProfile(userName + epamEmailExtension));
    	} catch (StudentNotFoundException e) {
    		return ResponseEntity.badRequest().body(null);
    	} catch (InvalidEmailException e) {
    		return ResponseEntity.badRequest().body(null);
    	}
    }

    /**
     * Get student details by email.
     * 
     * @param userName Username of student.
     * @return Return Student based on username.
     */
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("{userName}/email")
    public ResponseEntity<StudentModel> getStudentByEmail(@PathVariable String userName) {
        try {
            return ResponseEntity.ok().body(studentService.getStudentByEmail(userName + epamEmailExtension));
        } catch (InvalidEmailException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Get trainer of the student
     * 
     * @param userName Username of student.
     * @return Return trainer of student.
     */
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("{userName}/trainer")
    public ResponseEntity<UserProfileModel> getTrainer(@PathVariable String userName) {
        try {
            return ResponseEntity.ok().body(studentService.getTrainer(userName + epamEmailExtension));
        } catch (InvalidEmailException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoTrainerAssignedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
