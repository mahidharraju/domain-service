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
public class TrustGroupUpdateDTO {

  private UUID trustGroupId;
  private String name;
  private Boolean canViewSharedFilesAndFolders;
  private Boolean canCommentOnSharedFilesAndFolders;
  private Boolean canEditSharedDrivesAndFolders;
  private Boolean canTransferOwnership;
}
