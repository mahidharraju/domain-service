package com.org.domainservice.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrustGroupUpdateRequestDTO {

  private UUID id;
  private String title;
  private List<TrustGroupUpdateDTO> trustGroups;
}
