package com.org.domainservice.exception;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.org.domainservice.util.ControllerResponse;

@ControllerAdvice
public class DomainServiceExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> handleResourceNotFoundException(
      final ResourceNotFoundException exception,
      final WebRequest request) {
    return ControllerResponse.getNotFoundResponseEntity(
        buildAppSpecificErrorDetails(request, exception));
  }

  @ExceptionHandler(GenericAPIException.class)
  public ResponseEntity<?> handleGenericAPIException(
      final GenericAPIException exception,
      final WebRequest request) {
    return ControllerResponse.getServerErrorResponseEntity(
        buildAppSpecificErrorDetails(request, exception));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(
      final Exception exception,
      final WebRequest request) {
    return ControllerResponse.getServerErrorResponseEntity(
        buildAppSpecificErrorDetails(request, exception));
  }

  private ErrorDetails buildAppSpecificErrorDetails(WebRequest request, Exception exception) {
    return ErrorDetails
        .builder()
        .timeStamp(LocalDateTime.now())
        .message(exception.getMessage())
        .info(request.getDescription(false))
        .build();
  }
}
