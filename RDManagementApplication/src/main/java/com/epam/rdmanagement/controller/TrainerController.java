package com.epam.rdmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.epam.rdmanagement.exception.MentorNotAvailableException;
import com.epam.rdmanagement.exception.StudentNotFoundException;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.service.TrainerService;

/**
 * 
 * @author Anand_Edasseril
 *
 *         Trainer controller.
 */
@RestController
@RequestMapping("/profile")
public class TrainerController {

    @Autowired
    TrainerService trainerService;
    
    /**
     * Epam email extension.
     */
    @Value("${epam-email-extension}")
    private String epamEmailExtension;
    
    /**
     * Get profile of a trainer.
     * 
     * @param userName Username of the trainer.
     * @return Returns all the profile details related to a user.
     */
    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping("trainer/{userName}")
    public ResponseEntity<UserProfileModel> getTrainerProfile(@PathVariable String userName) {
        return ResponseEntity.ok().body(trainerService.getProfile(userName + epamEmailExtension));
    }

    /**
     * Get all students under a trainer.
     * 
     * @param trainerId Trainer username.
     * @return Return list of students
     * @throws StudentNotFoundException
     */
    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping("{trainerUsername}/trainees")
    public ResponseEntity<List<UserProfileModel>> getAllStudentsUnderTrainer(@PathVariable String trainerUsername)
            throws StudentNotFoundException {
            return ResponseEntity.ok().body(trainerService.getAllStudentsUnderTrainer(trainerUsername + epamEmailExtension));
    }

    /**
     * Get all mentors.
     * 
     * @return Returns list of mentors.
     * @throws MentorNotAvailableException
     */
    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping("{trainerId}/all/mentors/list")
    public ResponseEntity<List<UserProfileModel>> getAllMentors() throws MentorNotAvailableException {
            return ResponseEntity.ok().body(trainerService.getAllMentors());
    }
}
