package com.org.domainservice.model.id;

import java.io.Serializable;
import java.util.UUID;

public class DomainTrustGroupId implements Serializable {

  private static final long serialVersionUID = 6878190285512355786L;

  private UUID id;
  private UUID domainId;
  private UUID deptId;
  private UUID orgCollabId;

  public DomainTrustGroupId() {
    super();
  }

  public DomainTrustGroupId(
      final UUID id,
      final UUID domainId,
      final UUID deptId,
      final UUID orgCollabId) {
    super();
    this.id = id;
    this.domainId = domainId;
    this.deptId = deptId;
    this.orgCollabId = orgCollabId;
  }

  public UUID getId() {
    return id;
  }

  public UUID getDomainId() {
    return domainId;
  }

  public UUID getDeptId() {
    return deptId;
  }

  public UUID getOrgCollabId() {
    return orgCollabId;
  }


  public boolean equals(Object o) {
    return ((o instanceof DomainTrustGroupId)
        && id == ((DomainTrustGroupId) o).getId()
        && domainId == ((DomainTrustGroupId) o).getDomainId()
        && deptId == ((DomainTrustGroupId) o).getDeptId()
        && orgCollabId == ((DomainTrustGroupId) o).getOrgCollabId());
  }
}
