package com.org.domainservice.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControllerResponse {

  private ControllerResponse() {
  }

  public static <T> ResponseEntity<T> getOkResponseEntity(final T response) {
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  public static <T> ResponseEntity<T> getCreatedResponseEntity(final T response) {
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  public static <T> ResponseEntity<T> getServerErrorResponseEntity(final T details) {
    return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public static <T> ResponseEntity<T> getNotFoundResponseEntity(final T details) {
    return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
  }
}
