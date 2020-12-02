package com.org.domainservice.dto;

import lombok.Builder;

@Builder
public class RoleUpdateRequestDTO {

  private String domain;
  private String role;
}
