package com.org.domainservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleUpdateRequestDTO {

  private String domain;
  private String role;
}
