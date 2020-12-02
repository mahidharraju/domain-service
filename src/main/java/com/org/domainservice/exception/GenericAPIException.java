package com.org.domainservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericAPIException extends RuntimeException {

  public GenericAPIException(final String message) {
    super(message);
  }

  public GenericAPIException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
