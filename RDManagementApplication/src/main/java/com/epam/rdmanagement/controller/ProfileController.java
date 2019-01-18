package com.epam.rdmanagement.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.rdmanagement.exception.OperationCouldNotBeProcessed;
import com.epam.rdmanagement.model.FeedbackAddModel;
import com.epam.rdmanagement.model.RoleModel;
import com.epam.rdmanagement.service.AdminService;
import com.epam.rdmanagement.service.MenteeService;
import com.epam.rdmanagement.service.MentorService;
import com.epam.rdmanagement.service.ProfileService;
import com.epam.rdmanagement.service.StudentService;
import com.epam.rdmanagement.service.SuperAdminService;
import com.epam.rdmanagement.service.TrainerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * @author Anand_Edasseril
 *
 *         Profile controller
 */

@RestController
@RequestMapping("/profile")
public class ProfileController {

  /**
   * Autowire profile srvice.
   */
  @Autowired
  ProfileService profileService;

  @Autowired
  StudentService studentService;

  @Autowired
  TrainerService trainerService;

  @Autowired
  MentorService mentorService;

  @Autowired
  MenteeService menteeService;

  @Autowired
  AdminService adminService;

  @Autowired
  SuperAdminService superAdminService;

  @Autowired
  private ObjectMapper mapper;

  /**
   * Epam email extension.
   */
  @Value("${epam-email-extension}")
  private String epamEmailExtension;

  /**
   * Feedback added success message.
   */
  @Value("${feedback-added.message}")
  private String feedbackAddedMessage;

  /**
   * Operation could not be processed error message.
   */
  @Value("${operation-could-not-be-processed}")
  private String operationCouldNotBeProcessedMessage;

  @Value("${host.url}")
  private String hostURI;

  /**
   * Get the role of a particular user.
   * 
   * @param userName Username of the user.
   * @return Returns the role of the user.
   */
  @GetMapping("{userName}/role")
  @PreAuthorize("hasRole('TRAINER') or hasRole('STUDENT') or hasRole('MENTOR') or hasRole('MENTEE') or hasRole('ADMIN') or hasRole('SUPERADMIN')")
  public ResponseEntity<RoleModel> getRole(@PathVariable String userName) {
    return ResponseEntity.ok().body(profileService.getRole(userName + epamEmailExtension));
  }

  /**
   * Give feedback to a user.
   * 
   * @param feedbackAddModel
   * @return
   * @throws JsonProcessingException
   * @throws URISyntaxException
   */
  @PostMapping("{userName}/add/feedback")
  @PreAuthorize("hasRole('TRAINER') or hasRole('STUDENT') or hasRole('MENTOR') or hasRole('MENTEE')")
  public ResponseEntity<String> giveFeedback(@Valid @RequestBody FeedbackAddModel feedbackAddModel)
      throws JsonProcessingException, URISyntaxException {
    ResponseEntity<String> response = null;
    JsonNode responseJson = mapper.createObjectNode();
    StringBuffer createdResourceURI = new StringBuffer(hostURI);
    try {
      profileService.giveFeedback(feedbackAddModel);
      ((ObjectNode) responseJson).put("status", HttpStatus.CREATED.value());
      ((ObjectNode) responseJson).put("message", feedbackAddedMessage);
      response = ResponseEntity.created(new URI(createdResourceURI.toString()))
          .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseJson));
    } catch (OperationCouldNotBeProcessed e) {
      ((ObjectNode) responseJson).put("status", HttpStatus.BAD_REQUEST.value());
      ((ObjectNode) responseJson).put("message", operationCouldNotBeProcessedMessage);
      response = ResponseEntity.created(new URI(createdResourceURI.toString()))
          .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseJson));
    }

    return response;
  }

}
