package com.org.domainservice.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DomainUpdateRequestDTO {

  private UUID domainId;
  private UUID trustGroupId;
  private String trustGroupName;
}
