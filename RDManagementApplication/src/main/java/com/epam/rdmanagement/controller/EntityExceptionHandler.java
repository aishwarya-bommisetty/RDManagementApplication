package com.epam.rdmanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * This handler is required to handle the exceptions arising when any invalid
 * input is provided by the user to the controller endpoint.
 */
@ControllerAdvice(basePackageClasses = RegistrationController.class)
@RestController
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {
  @Autowired
  private ObjectMapper mapper;
  @Value("${title.status}")
  private String statusTitle;
  @Value("${title.message}")
  private String messageTitle;

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    JsonNode responseJson = mapper.createObjectNode();
    ResponseEntity<Object> response = null;

    ((ObjectNode) responseJson).put(statusTitle, HttpStatus.BAD_REQUEST.value());
    ((ObjectNode) responseJson).put(messageTitle,
        ex.getBindingResult().getFieldError().getDefaultMessage().toString());

    try {
      response = ResponseEntity.badRequest()
          .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseJson));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return response;
  }
}

