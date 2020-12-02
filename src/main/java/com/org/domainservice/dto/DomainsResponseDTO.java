package com.org.domainservice.dto;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DomainsResponseDTO {

  private Long id;
  private String title;
  private List<TrustGroupUpdateDTO> trustGroups;
  private List<DomainDTO> domains;
}

