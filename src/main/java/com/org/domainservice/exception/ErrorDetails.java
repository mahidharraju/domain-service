package com.org.domainservice.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetails {

  private String message;
  private LocalDateTime timeStamp;
  private String info;
}
