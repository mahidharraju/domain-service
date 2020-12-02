package com.org.domainservice.model.id;

import java.io.Serializable;

public class DomainTrustGroupId implements Serializable {

  private static final long serialVersionUID = 6878190285512355786L;

  private Long id;
  private Long domainId;
  private Long deptId;
  private Long orgCollabId;

  public DomainTrustGroupId() {
    super();
  }

  public DomainTrustGroupId(
      final Long id,
      final Long domainId,
      final Long deptId,
      final Long orgCollabId) {
    super();
    this.id = id;
    this.domainId = domainId;
    this.deptId = deptId;
    this.orgCollabId = orgCollabId;
  }

  public Long getId() {
    return id;
  }

  public Long getDomainId() {
    return domainId;
  }

  public Long getDeptId() {
    return deptId;
  }

  public Long getOrgCollabId() {
    return orgCollabId;
  }


  public boolean equals(Object o) {
    return ((o instanceof DomainTrustGroupId)
        && id == ((DomainTrustGroupId) o).getId()
        && domainId == ((DomainTrustGroupId) o).getDomainId()
        && deptId == ((DomainTrustGroupId) o).getDeptId()
        && orgCollabId == ((DomainTrustGroupId) o).getOrgCollabId());
  }

  @Override
  public int hashCode() {
    return id.hashCode()+domainId.hashCode()+deptId.hashCode()+orgCollabId.hashCode();
  }
}
