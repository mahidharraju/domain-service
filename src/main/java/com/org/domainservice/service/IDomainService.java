package com.org.domainservice.service;

import com.org.domainservice.dto.DomainDTO;
import com.org.domainservice.dto.DomainUpdateRequestDTO;
import com.org.domainservice.dto.DomainsResponseDTO;

public interface IDomainService {

  DomainsResponseDTO getAllDomainsForADepartment(
      final Long deptId,
      final Long orgCollabId);

  DomainDTO updateDomainTrustGroup(
      final DomainUpdateRequestDTO request,
      final Long deptId,
      final Long orgCollabId);
}
