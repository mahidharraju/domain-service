package com.org.domainservice.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrustGroupDTO {
  private Long trustGroupId;
  private String name;
  private Map<String, Boolean> permissions;
}
