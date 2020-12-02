package com.org.domainservice.dto;

import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrustGroupDTO {
  private UUID trustGroupId;
  private String name;
  private Map<String, Boolean> permissions;
}
