package com.org.domainservice.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrustGroupUpdateRequestDTO {

  private Long id;
  private String title;
  private List<TrustGroupUpdateDTO> trustGroups;
}
