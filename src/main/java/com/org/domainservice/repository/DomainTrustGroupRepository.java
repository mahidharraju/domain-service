package com.org.domainservice.repository;

import com.org.domainservice.model.DomainTrustGroup;
import com.org.domainservice.model.id.DomainTrustGroupId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainTrustGroupRepository extends JpaRepository<DomainTrustGroup, DomainTrustGroupId> {


  Optional<List<DomainTrustGroup>> findByDeptIdAndOrgCollabId(Long deptId, Long orgCollabId);

  Optional<DomainTrustGroup> findByDeptIdAndOrgCollabIdAndDomainId(Long deptId, Long orgCollabId, Long domainId);
}
