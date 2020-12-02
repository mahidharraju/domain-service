package com.org.domainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DomainUpdateRequestDTO {

  private Long domainId;
  private Long trustGroupId;
  private String trustGroupName;
}
