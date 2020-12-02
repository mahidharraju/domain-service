package com.org.domainservice.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class DomainDTO {

  private Long id;
  private String domainName;
  private int trustScore;
  private Long trustGroupId;
  private String trustGroupName;
  private String relationship;
}
