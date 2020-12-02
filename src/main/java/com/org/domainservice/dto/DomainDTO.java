package com.org.domainservice.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DomainDTO {

  private UUID id;
  private String domainName;
  private int trustScore;
  private UUID trustGroupId;
  private String trustGroupName;
  private String relationship;
}
