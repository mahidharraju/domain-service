package com.org.domainservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatabaseException extends RuntimeException {

  public DatabaseException(final String message) {
    super(message);
  }

  public DatabaseException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
