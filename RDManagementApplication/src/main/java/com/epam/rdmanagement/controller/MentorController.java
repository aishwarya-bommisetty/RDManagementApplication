package com.epam.rdmanagement.controller;

import java.util.List;

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
import com.epam.rdmanagement.exception.MenteesNotFoundException;
import com.epam.rdmanagement.model.MenteeModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.service.MentorService;

/**
 * 
 * @author Anand_Edasseril
 * 
 */
@RestController
@RequestMapping("/profile")
public class MentorController {

	@Autowired
	MentorService mentorService;

	/**
	 * Epam email extension.
	 */
	@Value("${epam-email-extension}")
	private String epamEmailExtension;	

	/**
	 * Get profile of a mentor.
	 * 
	 * @param userName Username of the mentor.
	 * @return Returns all the profile details related to a user.
	 */
	@PreAuthorize("hasRole('MENTOR')")
	@GetMapping("mentor/{userName}")
	public ResponseEntity<UserProfileModel> getMentorProfile(@PathVariable String userName) {
		return ResponseEntity.ok().body(mentorService.getProfile(userName + epamEmailExtension));
	}

	/**
	 * Controller method to find all mentees under a mentor.
	 * 
	 * @param userName username of the user.
	 * @return Return List of mentee.
	 * @throws MenteeNotFoundException
	 */
	@PreAuthorize("hasRole('MENTOR')")
	@GetMapping("{userName}/mentees")
	public ResponseEntity<List<MenteeModel>> getAllMenteesUnderMentor(@PathVariable String userName) {

		try {
			return ResponseEntity.ok().body(mentorService.getAllMenteesUnderMentor(userName + epamEmailExtension));
		} catch (MenteesNotFoundException e) {
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
		}
	}

}
