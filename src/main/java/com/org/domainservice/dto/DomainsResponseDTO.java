package com.org.domainservice.dto;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class DomainsResponseDTO {


  private UUID id;
  private String title;
  private List<TrustGroupUpdateDTO> trustGroups;
  private List<DomainDTO> domains;
}

