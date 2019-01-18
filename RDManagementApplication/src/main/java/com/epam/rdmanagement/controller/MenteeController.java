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

import com.epam.rdmanagement.exception.MenteeNotFoundException;
import com.epam.rdmanagement.exception.MentorNotAvailableException;
import com.epam.rdmanagement.model.MenteeModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.service.MenteeService;


/**
 * 
 * @author Anand_Edasseril
 *
 */
@RestController
@RequestMapping("/profile")
public class MenteeController {

    @Autowired
    MenteeService menteeService;
    
    /**
     * Epam email extension.
     */
    @Value("${epam-email-extension}")
    private String epamEmailExtension;
    
    
    /**
     * Get profile of a mentee.
     * 
     * @param userName Username of the mentee.
     * @return Returns all the profile details related to a mentee.
     */
    @PreAuthorize("hasRole('MENTEE')")
    @GetMapping("mentee/{userName}")
    public ResponseEntity<MenteeModel> getMenteeProfile(@PathVariable String userName) {
      try {
        return ResponseEntity.ok().body(menteeService.getProfile(userName + epamEmailExtension));
      } catch (MenteeNotFoundException e) {
      
        return ResponseEntity.badRequest().body(null);
      }
    }

    /**
     * This method returns the mentor of a particular mentee.
     * @param userName Username of the mentee.
     * @return Returns the mentor of a mentee.
     */
    @PreAuthorize("hasRole('MENTEE')")
    @GetMapping("{userName}/mentor")
    public ResponseEntity<UserProfileModel> getMentor(@PathVariable String userName) {      
        try {
            return ResponseEntity.ok().body(menteeService.getMentor(userName + epamEmailExtension));
        } catch (MentorNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Get the details of a mentee by email.
     * @param userName username of the mentee.
     * @return mentee details.
     */
    @PreAuthorize("hasRole('MENTEE')")
    @GetMapping("email/{userName}")
    public ResponseEntity<MenteeModel> getMenteeByEmail(@PathVariable String userName) {
        
        try {
            return ResponseEntity.ok().body(menteeService.getMenteeByEmail(userName + epamEmailExtension));
        } catch (MenteeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
