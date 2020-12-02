package com.org.domainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TrustGroupUpdateDTO {

  private Long trustGroupId;
  private String name;
  private Boolean canViewSharedFilesAndFolders;
  private Boolean canCommentOnSharedFilesAndFolders;
  private Boolean canEditSharedDrivesAndFolders;
  private Boolean canTransferOwnership;
}
