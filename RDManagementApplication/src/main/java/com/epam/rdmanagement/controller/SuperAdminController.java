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

import com.epam.rdmanagement.exception.AdminNotFoundException;
import com.epam.rdmanagement.exception.OperationCouldNotBeProcessed;
import com.epam.rdmanagement.model.UserAddModel;
import com.epam.rdmanagement.model.UserProfileModel;
import com.epam.rdmanagement.service.SuperAdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * @author Anand_Edasseril
 *
 *         Super admin controller.
 */
@RestController
@RequestMapping("/profile")
public class SuperAdminController {

    /**
     * Autowire super admin service.
     */
    @Autowired
    SuperAdminService superAdminService;

    @Autowired
    private ObjectMapper mapper;

    /**
     * Success message when admin is added successfully.
     */
    @Value("${admin-added.message}")
    private String adminAddedMessage;
    
    /**
     * Epam email extension.
     */
    @Value("${epam-email-extension}")
    private String epamEmailExtension;

    @Value("${host.url}")
    private String hostURI;
    
    /**
     * Get profile of a super admin.
     * 
     * @param userName Username of the super admin.
     * @return Returns all the profile details related to a user.
     */
    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping("{userName}")
    public ResponseEntity<UserProfileModel> getSuperAdminProfile(@PathVariable String userName) {
        return ResponseEntity.ok().body(superAdminService.getProfile(userName + epamEmailExtension));
    }

    /**
     * It gives all the admin list.
     * 
     * @param userName Username of the user calling the api.
     * @return Returns the list of admins if the user is a super admin.
     * @throws AdminNotFoundException 
     */
    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping("{userName}/admins")
    public ResponseEntity<List<UserProfileModel>> getAllAdmins() throws AdminNotFoundException {
            return ResponseEntity.ok().body(superAdminService.getAllAdmins());
    }

    /**
     * Allows a super admin to add an admin.
     * 
     * @param user     Admin to be added.
     * @param userName Username of the user calling the api.
     * @return Returns a string showing the status.
     * @throws OperationCouldNotBeProcessed
     */
    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping("{userName}/add/admin")
    public ResponseEntity<String> addAdmin(@Valid @RequestBody UserAddModel user, @PathVariable String userName)
            throws JsonProcessingException, URISyntaxException, OperationCouldNotBeProcessed {
        ResponseEntity<String> response = null;
        JsonNode responseJson = mapper.createObjectNode();
        StringBuffer createdResourceURI = new StringBuffer(hostURI);
            superAdminService.addAdmin(user);
            ((ObjectNode) responseJson).put("status", HttpStatus.CREATED.value());
            ((ObjectNode) responseJson).put("message", adminAddedMessage);
            response = ResponseEntity.created(new URI(createdResourceURI.toString()))
                    .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseJson));

        return response;
    }
    
}
