package com.org.domainservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.domainservice.model.DomainTrustGroup;
import com.org.domainservice.model.id.DomainTrustGroupId;

public interface DomainTrustGroupRepository extends JpaRepository<DomainTrustGroup, DomainTrustGroupId> {


  List<DomainTrustGroup> findByDeptIdAndOrgCollabId(UUID deptId, UUID orgCollabId);

  Optional<DomainTrustGroup> findByDeptIdAndOrgCollabIdAndDomainId(UUID deptId, UUID orgCollabId, UUID domainId);
}
