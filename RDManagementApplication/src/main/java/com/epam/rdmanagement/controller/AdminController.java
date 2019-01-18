package com.epam.rdmanagement.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
import com.epam.rdmanagement.model.UserAddModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * @author Anand_Edasseril
 *
 *         Admin controller.
 * 
 */
@RestController
@RequestMapping("/profile")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    private ObjectMapper mapper;

    /**
     * Success message when mentor is added successfully.
     */
    @Value("${mentor-added.message}")
    private String mentorAddedMessage;
    
    /**
     * Success message when mentor is added successfully.
     */
    @Value("${operation-could-not-be-processed}")
    private String operationCouldNotBeProcessed;

    /**
     * Success message when trainer is added successfully.
     */
    @Value("${trainer-added.message}")
    private String trainerAddedMessage;

    /**
     * The host URI.
     */
    @Value("${host.url}")
    private String hostURI;
   
    /**
     * Epam email extension.
     */
    @Value("${epam-email-extension}")
    private String epamEmailExtension;
    
    /**
     * Get profile of a admin.
     * 
     * @param userName Username of the admin.
     * @return Returns all the profile details related to a user.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin/{userName}")
    public ResponseEntity<UserProfileModel> getAdminProfile(@PathVariable String userName) {
        return ResponseEntity.ok().body(adminService.getProfile(userName + epamEmailExtension));
    }
    
    /**
     * Allows admin to add trainers.
     * 
     * @param user     Trainer to be added.
     * @param userName Username of the user calling the api.
     * @return Returns the status message.
     * @throws URISyntaxException 
     * @throws JsonProcessingException 
     * @throws OperationCouldNotBeProcessed
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{userName}/add/trainer")
    public ResponseEntity<String> addTrainer(@Valid @RequestBody UserAddModel user, @PathVariable String userName) throws JsonProcessingException, URISyntaxException {
        ResponseEntity<String> response = null;
        JsonNode responseJson = mapper.createObjectNode();
        StringBuffer createdResourceURI = new StringBuffer(hostURI);

        try {
            adminService.addTrainer(user);
            ((ObjectNode) responseJson).put("status", HttpStatus.CREATED.value());
            ((ObjectNode) responseJson).put("message", trainerAddedMessage);
            response = ResponseEntity.created(new URI(createdResourceURI.toString()))
                    .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseJson));

        } catch (OperationCouldNotBeProcessed e) {

            ((ObjectNode) responseJson).put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
            ((ObjectNode) responseJson).put("message", operationCouldNotBeProcessed);
            response = ResponseEntity.created(new URI(createdResourceURI.toString()))
                    .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseJson));
        }

        return response;
    }

    /**
     * This method allows an admin to add a mentor.
     * 
     * @param user     Mentor to be added.
     * @param userName Username of the user calling the api.
     * @return Returns the status message.
     * @throws OperationCouldNotBeProcessed
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{userName}/add/mentor")
    public ResponseEntity<String> addMentor(@Valid @RequestBody UserAddModel user, @PathVariable String userName)
            throws JsonProcessingException, URISyntaxException {
        ResponseEntity<String> response = null;
        JsonNode responseJson = mapper.createObjectNode();
        StringBuffer createdResourceURI = new StringBuffer(hostURI);

        try {

            adminService.addMentor(user);
            ((ObjectNode) responseJson).put("status", HttpStatus.CREATED.value());
            ((ObjectNode) responseJson).put("message", mentorAddedMessage);
            response = ResponseEntity.created(new URI(createdResourceURI.toString()))
                    .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseJson));

        } catch (OperationCouldNotBeProcessed e) {
            ((ObjectNode) responseJson).put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
            ((ObjectNode) responseJson).put("message", operationCouldNotBeProcessed);
            response = ResponseEntity.created(new URI(createdResourceURI.toString()))
                    .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseJson));
        }
        return response;
    }

    /**
     * This method returns the list of all trainers if the user calling the api is
     * either admin or super admin.
     * 
     * @param userName Username of the user calling the api.
     * @return Returns the list of all trainers.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{userName}/all/trainers")
    public ResponseEntity<List<UserProfileModel>> getAllTrainers() {
            return ResponseEntity.ok().body(adminService.getAllTrainers());
    }

    /**
     * This method returns the list of all mentors if the user calling the api is
     * either admin or super admin.
     * 
     * @param userName Username of the user calling the api.
     * @return Returns the list of all mentors.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{userName}/all/mentors")
    public ResponseEntity<List<UserProfileModel>> getAllMentors() {
            return ResponseEntity.ok().body(adminService.getAllMentors());
    }

    /**
     * This method returns the list of all students if the user calling the api is
     * either admin or super admin.
     * 
     * @param userName Username of the user calling the api.
     * @return Returns the list of all students.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{userName}/all/students")
    public ResponseEntity<List<UserProfileModel>> getAllStudents() {
            return ResponseEntity.ok().body(adminService.getAllStudents());
    }
}
