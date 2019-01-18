package com.epam.rdmanagement.controller;

import com.epam.rdmanagement.exception.UserAlreadyExistsException;
import com.epam.rdmanagement.model.UserModel;
import com.epam.rdmanagement.service.RegistrationService;
import com.epam.rdmanagement.util.ConstantsUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
  @Autowired
  private RegistrationService registartionService;
  @Autowired
  private ObjectMapper mapper;
  @Value("${host.url}")
  private String hostUri;
  @Value("${profile.endpoint}")
  private String profileEndpoint;
  @Value("${register-success.message}")
  private String registerSuccessMessage;
  @Value("${user-already-exists.message}")
  private String alreadyExistsUserMessage;
  @Value("${title.status}")
  private String statusTitle;
  @Value("${title.message}")
  private String messageTitle;
  @Value("${title.resource-location}")
  private String resourceLocationTitle;
  @Value("${at-character}")
  private char atChar;

  /**
   * Controller endpoint of {@value ConstantsUtil.REGISTER_ENDPOINT} path from the
   * context. It uses {@link RegistrationService} service for registering a
   * student.
   *
   * @param user entity gets initialized from the json passed in request body.
   * @return {@link ResponseEntity} to respond with proper status codes and
   *         message.
   * @throws URISyntaxException 
   * @throws JsonProcessingException 
   * @throws UserAlreadyExistsException is while registering an already existing user
   */
  @PostMapping(ConstantsUtil.REGISTER_ENDPOINT)
  public ResponseEntity<String> registerStudent(@Valid @RequestBody UserModel user)
      throws URISyntaxException, JsonProcessingException {
    ResponseEntity<String> response = null;
    JsonNode responseJson = mapper.createObjectNode();
    StringBuffer createdResourceUri = new StringBuffer(hostUri);

    createdResourceUri.append(profileEndpoint);
    createdResourceUri
        .append(user.getEmail().substring(0, user.getEmail().indexOf(atChar)).toLowerCase());

    try {
      registartionService.registerStudent(user);
      ((ObjectNode) responseJson).put(statusTitle, HttpStatus.CREATED.value());
      ((ObjectNode) responseJson).put(messageTitle, registerSuccessMessage);
      ((ObjectNode) responseJson).put(resourceLocationTitle, createdResourceUri.toString());
      response = ResponseEntity.created(new URI(createdResourceUri.toString()))
          .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseJson));
    } catch (UserAlreadyExistsException e) {
      ((ObjectNode) responseJson).put(statusTitle, HttpStatus.CONFLICT.value());
      ((ObjectNode) responseJson).put(messageTitle, alreadyExistsUserMessage);
      response = ResponseEntity.status(HttpStatus.CONFLICT)
          .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseJson));
    }

    return response;
  }
}
